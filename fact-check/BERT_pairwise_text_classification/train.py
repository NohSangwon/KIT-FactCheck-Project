import argparse
import pickle
import torch
import torch.nn as nn
import torch.optim as optim
from pathlib import Path
from torch.utils.data import DataLoader
from transformers.modeling_bert import BertConfig
from pretrained.tokenization import BertTokenizer as ETRITokenizer
from gluonnlp.data import SentencepieceTokenizer
from model.net import PairwiseClassifier
from model.data import Corpus
from model.utils import PreProcessor, PadSequence
from model.metric import evaluate, acc
from utils import Config, CheckpointManager, SummaryManager
from tqdm import tqdm
from torch.utils.tensorboard import SummaryWriter

# for reproducibility
torch.manual_seed(777)
torch.backends.cudnn.deterministic = True
torch.backends.cudnn.benchmark = False

parser = argparse.ArgumentParser()
parser.add_argument('--data_dir', default='data', help="Directory containing config.json of data")
parser.add_argument('--model_dir', default='experiments/base_model', help="Directory containing config.json of model")
parser.add_argument('--type', default='skt', choices=['skt', 'etri'], required=True,  type=str)
parser.add_argument('--ptr_dir', default='pretrained')


if __name__ == '__main__':
    args = parser.parse_args()
    # ptr_dir = Path('pretrained')
    ptr_dir = Path(args.ptr_dir)
    data_dir = Path(args.data_dir)
    model_dir = Path(args.model_dir)

    ptr_config = Config(ptr_dir / 'config_{}.json'.format(args.type))
    data_config = Config(data_dir / 'config.json')
    model_config = Config(model_dir / 'config.json')

    # vocab
    with open(ptr_config.vocab, mode='rb') as io:
        vocab = pickle.load(io)

    # tokenizer
    # tokenizer
    if args.type == 'etri':
        ptr_tokenizer = ETRITokenizer.from_pretrained(ptr_config.tokenizer, do_lower_case=False)
        pad_sequence = PadSequence(length=model_config.length, pad_val=vocab.to_indices(vocab.padding_token))
        preprocessor = PreProcessor(vocab=vocab, split_fn=ptr_tokenizer.tokenize, pad_fn=pad_sequence)
    elif args.type == 'skt':
        ptr_tokenizer = SentencepieceTokenizer(ptr_config.tokenizer)
        pad_sequence = PadSequence(length=model_config.length, pad_val=vocab.to_indices(vocab.padding_token))
        preprocessor = PreProcessor(vocab=vocab, split_fn=ptr_tokenizer, pad_fn=pad_sequence)

    # model
    config = BertConfig(ptr_config.config)
    model = PairwiseClassifier(config, num_classes=model_config.num_classes, vocab=preprocessor.vocab)
    bert_pretrained = torch.load(ptr_config.bert)
    model.load_state_dict(bert_pretrained, strict=False)

    # training
    tr_ds = Corpus(data_config.train, preprocessor.preprocess)
    tr_dl = DataLoader(tr_ds, batch_size=model_config.batch_size, shuffle=True, num_workers=4, drop_last=True)
    val_ds = Corpus(data_config.validation, preprocessor.preprocess)
    val_dl = DataLoader(val_ds, batch_size=model_config.batch_size, num_workers=4)

    loss_fn = nn.CrossEntropyLoss()
    opt = optim.Adam(
        [
            {"params": model.bert.parameters(), "lr": model_config.learning_rate / 100},
            {"params": model.classifier.parameters(), "lr": model_config.learning_rate},

        ], weight_decay=5e-4)

    device = torch.device('cuda') if torch.cuda.is_available() else torch.device('cpu')
    model.to(device)

    writer = SummaryWriter('{}/runs_{}'.format(model_dir, args.type))
    checkpoint_manager = CheckpointManager(model_dir)
    summary_manager = SummaryManager(model_dir)
    best_val_loss = 1e+10

    for epoch in tqdm(range(model_config.epochs), desc='epochs'):

        tr_loss = 0
        tr_acc = 0

        model.train()
        for step, mb in tqdm(enumerate(tr_dl), desc='steps', total=len(tr_dl)):
            x_mb, x_types_mb, y_mb = map(lambda elm: elm.to(device), mb)
            opt.zero_grad()
            y_hat_mb = model(x_mb, x_types_mb)
            mb_loss = loss_fn(y_hat_mb, y_mb)
            mb_loss.backward()
            opt.step()

            with torch.no_grad():
                mb_acc = acc(y_hat_mb, y_mb)

            tr_loss += mb_loss.item()
            tr_acc += mb_acc.item()

            if (epoch * len(tr_dl) + step) % model_config.summary_step == 0:
                tr_loss /= (step + 1)
                tr_acc /= (step + 1)

                tr_summary = {'loss': tr_loss, 'acc': tr_acc}
                val_summary = evaluate(model, val_dl, {'loss': loss_fn, 'acc': acc}, device)

                writer.add_scalars('loss', {'train_loss': tr_summary['loss'], 'val_loss': val_summary['loss']}, epoch * len(tr_dl) + step)
                writer.add_scalars('accuracy', {'train_acc': tr_summary['acc'], 'val_acc': val_summary['acc']}, epoch * len(tr_dl) + step)

                tqdm.write('global_step : {:3}, tr_loss: {:.3f}, val_loss: '
                           '{:.3f}, tr_acc: {:.2%}, val_acc: {:.2%}'.format(epoch * len(tr_dl) + step, tr_summary['loss'],
                                                                            val_summary['loss'], tr_summary['acc'],
                                                                            val_summary['acc']))

                model.train()
        else:
            tr_loss /= (step + 1)
            tr_acc /= (step + 1)

            tr_summary = {'loss': tr_loss, 'acc': tr_acc}
            val_summary = evaluate(model, val_dl, {'loss': loss_fn, 'acc': acc}, device)
            tqdm.write('epoch : {}, tr_loss: {:.3f}, val_loss: '
                       '{:.3f}, tr_acc: {:.2%}, val_acc: {:.2%}'.format(epoch + 1, tr_summary['loss'],
                                                                        val_summary['loss'], tr_summary['acc'],
                                                                        val_summary['acc']))

            val_loss = val_summary['loss']
            is_best = val_loss < best_val_loss

            if is_best:
                state = {'epoch': epoch + 1,
                         'model_state_dict': model.state_dict(),
                         'opt_state_dict': opt.state_dict()}
                summary = {'train': tr_summary, 'validation': val_summary}

                summary_manager.update(summary)
                summary_manager.save('summary_{}.json'.format(args.type))
                checkpoint_manager.save_checkpoint(state, 'best_{}.tar'.format(args.type))

                best_val_loss = val_loss

