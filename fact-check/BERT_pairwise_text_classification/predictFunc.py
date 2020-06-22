# -*- coding: utf-8 -*- 

import os
import pickle
import torch
from pathlib import Path
from transformers.modeling_bert import BertConfig
from gluonnlp.data import SentencepieceTokenizer
from model.net import PairwiseClassifier
from model.utils import PreProcessor, PadSequence
from utils import Config, CheckpointManager, SummaryManager


def predict(sentence1, sentence2):
    ptr_dir = "C:/Users/aaaaa/workspace/fact-check/BERT_pairwise_text_classification/pretrained"
    data_dir = "C:/Users/aaaaa/workspace/fact-check/BERT_pairwise_text_classification/data"
    caseType = "skt"
    model_dir = "C:/Users/aaaaa/workspace/fact-check/BERT_pairwise_text_classification/experiments/base_model"
    checkpoint_model_file = "best_skt.tar"
    
    # ptr_dir = "BERT_pairwise_text_classification/pretrained"
    # data_dir = "BERT_pairwise_text_classification/data"
    # caseType = "skt"
    # model_dir = "BERT_pairwise_text_classification/experiments/base_model"
    # checkpoint_model_file = "best_skt.tar"
    
    # ptr_dir = "pretrained"
    # data_dir = "data"
    # caseType = "skt"
    # model_dir = "experiments/base_model"
    # checkpoint_model_file = "best_skt.tar"
    
    ptr_dir = Path(ptr_dir)
    data_dir = Path(data_dir)
    model_dir = Path(model_dir)
    checkpoint_model_file = Path(checkpoint_model_file)
    
    ptr_config = Config(ptr_dir / 'config_skt.json')
    data_config = Config(data_dir / 'config.json')
    model_config = Config(model_dir / 'config.json')
    
    # vocab
    with open(os.path.join(ptr_dir, ptr_config.vocab), mode='rb') as io:
        vocab = pickle.load(io)
    
    
    ptr_tokenizer = SentencepieceTokenizer(os.path.join(ptr_dir, ptr_config.tokenizer))
    pad_sequence = PadSequence(length=model_config.length, pad_val=vocab.to_indices(vocab.padding_token))
    preprocessor = PreProcessor(vocab=vocab, split_fn=ptr_tokenizer, pad_fn=pad_sequence)
    
    # model (restore)
    checkpoint_manager = CheckpointManager(model_dir)
    checkpoint = checkpoint_manager.load_checkpoint(checkpoint_model_file)
    config = BertConfig(os.path.join(ptr_dir, ptr_config.config))
    model = PairwiseClassifier(config, num_classes=model_config.num_classes, vocab=preprocessor.vocab)
    model.load_state_dict(checkpoint['model_state_dict'])
    
    device = torch.device('cpu')
    model.to(device)
    
    transform = preprocessor.preprocess
    if model.training:
        model.eval()
        
    indices, token_types = [torch.tensor([elm]) for elm in transform(sentence1, sentence2)]

    with torch.no_grad():
        label = model(indices, token_types)
    label = label.max(dim=1)[1]
    label = label.numpy()[0]

    return label