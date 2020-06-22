# -*- coding: utf-8 -*- 

import argparse
import pickle
import torch
from pathlib import Path
from transformers.modeling_bert import BertConfig
from pretrained.tokenization import BertTokenizer as ETRITokenizer
from gluonnlp.data import SentencepieceTokenizer
from model.net import PairwiseClassifier
from model.utils import PreProcessor, PadSequence
from utils import Config, CheckpointManager, SummaryManager

from torch.utils.tensorboard import SummaryWriter

# for reproducibility
torch.manual_seed(777)
torch.backends.cudnn.deterministic = True
torch.backends.cudnn.benchmark = False

parser = argparse.ArgumentParser()
parser.add_argument('--data_dir', default='data', help="Directory containing config.json of data")
parser.add_argument('--model_dir', default='experiments/base_model', help="Directory containing config.json of model")
parser.add_argument('--type', default='skt', choices=['skt', 'etri'], type=str)
parser.add_argument('--ptr_dir', default='pretrained')
parser.add_argument('--checkpoint_model_file', default='best_skt.tar', type=str)

if __name__ == '__main__':
    args = parser.parse_args()
    ptr_dir = Path(args.ptr_dir)
    data_dir = Path(args.data_dir)
    model_dir = Path(args.model_dir)
    checkpoint_model_file = Path(args.checkpoint_model_file)

    ptr_config = Config(ptr_dir / 'config_{}.json'.format(args.type))
    data_config = Config(data_dir / 'config.json')
    model_config = Config(model_dir / 'config.json')

    # vocab
    with open(ptr_config.vocab, mode='rb') as io:
        vocab = pickle.load(io)

    # tokenizer
    if args.type == 'etri':
        ptr_tokenizer = ETRITokenizer.from_pretrained(ptr_config.tokenizer, do_lower_case=False)
        pad_sequence = PadSequence(length=model_config.length, pad_val=vocab.to_indices(vocab.padding_token))
        preprocessor = PreProcessor(vocab=vocab, split_fn=ptr_tokenizer.tokenize, pad_fn=pad_sequence)
    elif args.type == 'skt':
        ptr_tokenizer = SentencepieceTokenizer(ptr_config.tokenizer)
        pad_sequence = PadSequence(length=model_config.length, pad_val=vocab.to_indices(vocab.padding_token))
        preprocessor = PreProcessor(vocab=vocab, split_fn=ptr_tokenizer, pad_fn=pad_sequence)

    # model (restore)
    checkpoint_manager = CheckpointManager(model_dir)
    checkpoint = checkpoint_manager.load_checkpoint(checkpoint_model_file)
    config = BertConfig(ptr_config.config)
    model = PairwiseClassifier(config, num_classes=model_config.num_classes, vocab=preprocessor.vocab)
    model.load_state_dict(checkpoint['model_state_dict'])

    # device = torch.device('cuda') if torch.cuda.is_available() else torch.device('cpu')
    device = torch.device('cpu')
    model.to(device)

    transform = preprocessor.preprocess
    if model.training:
      model.eval()

    while True:
      inputStr = input("입력-'문장|문장'(종료:exit): ")
      if (inputStr == "exit"):
        break
      sentence1, sentence2 = inputStr.split('|')

      indices, token_types = [torch.tensor([elm]) for elm in transform(sentence1, sentence2)]
      indices.to(device)
      token_types.to(device)

      with torch.no_grad():
        label = model(indices, token_types)
      label = label.max(dim=1)[1]
      label = label.numpy()[0]

      if label == 0:
        print("판별결과: false, 다름")
      elif label == 1:
        print("판별결과: true, 같음")
        

