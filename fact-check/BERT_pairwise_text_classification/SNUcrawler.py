import requests
from bs4 import BeautifulSoup
import re
import urllib.request
from selenium import webdriver
import json
from urllib import parse

# 검증 기사 제목
def getArticle(id):
    url = "https://factcheck.snu.ac.kr/v2/facts/" + id
    req = urllib.request.Request(url)
    html = requests.get(url)
    html_txt = html.text
    soup = BeautifulSoup(html.text, 'html.parser')
    vf_article = soup.select('div > div.fcItem_vf_li_right > div.vf_exp_wrap > ul > li > p.vf_article > a')
    vf_article_title = vf_article[0].text  # 타이틀
    return vf_article_title


# 검증 기사 링크
def getArticleLink(id):
    url = "https://factcheck.snu.ac.kr/v2/facts/" + id
    sourcecode = urllib.request.urlopen(url).read()
    soup = BeautifulSoup(sourcecode, "html.parser")

    href = soup.find("p", class_="vf_article").find_all("a")
    link = (str(href[0]))
    link_list = []
    for i in range(0, len(link)):
        if link[i] == '"':
            link_list.append(i)
    return link[link_list[0] + 1:link_list[1]]


def getCheckPlZtitle(id):
    url = "https://factcheck.snu.ac.kr/v2/facts/" + id
    sourcecode = urllib.request.urlopen(url).read()
    soup = BeautifulSoup(sourcecode, "html.parser")

    title = soup.select(
        '#content_detail > div > div.fcItem_detail_wrap > div.fcItem_detail > div > div.prg.fcItem_detail_li > div.fcItem_detail_li_p > p:nth-of-type(1) > a')
    checktitle = title[0].text
    return checktitle

def getNewSNUData(startPage, endPage):
    driver = webdriver.Chrome('C:/Users/aaaaa/workspace/fact-check/BERT_pairwise_text_classification/chromDriver/chromedriver')
    
    result = []
    for i in range(startPage, endPage + 1):
        url = "https://factcheck.snu.ac.kr/v2/facts?page=" + str(i)
        html = requests.get(url)
        html_txt = html.text
        soup = BeautifulSoup(html.text, 'html.parser')

        # 뉴스기사 id 번호 리스트
        p = re.compile(r'id\d\d*')
        temp = p.findall(html_txt)
        temp2 = []
        for j in range(0, 14, 2):
            temp2.append(temp[j])
        news_id_list = []
        for j in range(0, 7):
            news_id_list.append(temp2[j][2:])

        CheckTitleList = []
        ArticleTitleList = []
        ArticleLinkList = []
        for j in range(0, 7):
            ArticleTitleList.append(getArticle(news_id_list[j]))
            ArticleLinkList.append(getArticleLink(news_id_list[j]))
            CheckTitleList.append(getCheckPlZtitle(news_id_list[j]))

        # 뉴스 점수
        driver.get(url)
        html = driver.page_source  ## 페이지의 elements모두 가져오기
        p = re.compile(r'truth-o-meter score\d')
        temp = p.findall(html)
        score_list = []
        for tmp in temp:
            score_list.append(tmp[-1])
        # 한페이지에 7개의 기사 (맨 앞에는 공백으로 나옴)

        for j in range(0, 7):
            item = {
                'score': score_list[j],
                'title': CheckTitleList[j],
                'url': ArticleLinkList[j]
            }
            result.append(item)
    resultJsonStr = json.dumps(result, ensure_ascii=False)
    
    driver.close()
    
    return resultJsonStr
getNewSNUData(1, 5)