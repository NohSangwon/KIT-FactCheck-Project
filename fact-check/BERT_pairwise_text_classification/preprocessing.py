import os
import re
import tensorflow as tf
a = tf.zeros()

# contentList = list()
# with open("./data/data_PASW_ko.tsv", 'r', encoding='UTF8') as file:
#     for line in file.readlines():
#         tmp = line.strip().split('\t')
#         print(tmp)
#         if (tmp[1] == ""):
#             continue
#
#         if (tmp[1] == "NS" or tmp[2] == "NS"):
#             continue
#
#         content = {
#             "id": tmp[0],
#             "str1": tmp[1],
#             "str2": tmp[2],
#             "quality": tmp[3],
#         }
#         contentList.append(content)
#
# count = 0
# for content in contentList:
#     saveFileDir = './data/data_PASW_ko2.tsv'
#
#     count += 1
#     with open(saveFileDir, 'a', encoding='UTF8') as file:
#         file.write(content["str1"] + "\t")
#         file.write(content["str2"] + "\t")
#         file.write(content["quality"] + "\n")
#
# print(count)

# import pandas as pd
#
# filepath = "./data/train.txt"
# corpus = pd.read_csv(filepath, sep='|', error_bad_lines=False)
# # self._corpus = pd.read_csv(filepath, sep='\t')
# # corpus = corpus.dropna()
# for idx in range(len(corpus.values)):
#     tmp = corpus.values[idx][2]
#     if type(tmp) is not int:
#
#         print(idx)
#         print(corpus.values[idx][0])
#         print(corpus.values[idx][1])
#         print(tmp)
#         print(idx, type(corpus.values[idx][0]), type(corpus.values[idx][1]), tmp)
#         corpus.values[idx][2] = int(tmp)
#         print(type(tmp))
