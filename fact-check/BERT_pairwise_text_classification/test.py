from konlpy.tag import Kkma
from konlpy.utils import pprint
kkma = Kkma()
pprint(kkma.nouns('명사만을 추출하여 워드클라우드를 그려봅니다'))
['명사', '추출', '워드', '워드클라우드', '클라우드']