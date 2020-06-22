package kr.or.kit.factcheck.CompareSentenceModule;

import java.util.List;
import java.util.Vector;

import kr.bydelta.koala.data.Morpheme;
import kr.bydelta.koala.data.Sentence;
import kr.bydelta.koala.data.Word;
import kr.bydelta.koala.hnn.Tagger;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.or.kit.factcheck.dto.CompareResult;

public class SentenceAnalyzer {
	private Komoran komoran;

	SentenceAnalyzer() {
		komoran = new Komoran(DEFAULT_MODEL.FULL);
	}

	private static class LazyHolder {
		public static final SentenceAnalyzer INSTANCE = new SentenceAnalyzer();
	}

	public static SentenceAnalyzer getInstance() {
		return LazyHolder.INSTANCE;
	}

	public Vector<String> getNouns(String sentence) {
		Vector<String> result = new Vector<String>();

		KomoranResult analyzeResultList = komoran.analyze(sentence);
		List<String> nouns = analyzeResultList.getNouns();
		if (nouns.size() != 0) {
			for (int i = 0; i < nouns.size(); i++) {
				result.add(nouns.get(i));
			}
		} else {
			Tagger tagger = new Tagger();
			List<Sentence> sentences = tagger.tag(sentence);
			for (Sentence sent : sentences) {
				for (Word word : sent) {
					for (Morpheme morph : word) {
						String tag = morph.getTag().toString();
						if (tag.equals("NNG") || tag.equals("NNP"))
							result.add(morph.getSurface());
					}
				}
			}
		}
		if (result.size() == 0) {
			kr.bydelta.koala.okt.Tagger tagger = new kr.bydelta.koala.okt.Tagger();
			List<Sentence> sentences = tagger.tag(sentence);
			for (Sentence sent : sentences) {
				for (Word word : sent) {
					for (Morpheme morph : word) {
						String tag = morph.getTag().toString();
						if (tag.equals("NNG") || tag.equals("NNP"))
							result.add(morph.getSurface());
					}
				}
			}
		}

		return result;
	}

	public Vector<CompareResult> sentenceSplitter(String sentence) {
		Vector<CompareResult> result = new Vector<CompareResult>();
		kr.bydelta.koala.okt.SentenceSplitter splitter = new kr.bydelta.koala.okt.SentenceSplitter();

		List<String> split = splitter.sentences(sentence);
		Vector<Integer> idxList = new Vector<>();
		Vector<Integer> lengthList = new Vector<>();
		idxList.add(0);
		lengthList.add(0);

		for (int i = 0; i < split.size(); i++) {
			int begin = 0;
			int end = sentence.indexOf(split.get(i));
			
			String preBlank = sentence.substring(begin, end);
			sentence = sentence.substring(end + split.get(i).length());
			
			result.add(new CompareResult(split.get(i), "", "", -1, 0, -1, preBlank));
		}

		return result;
	}

	public static void main(String[] args) {
		SentenceAnalyzer cv = SentenceAnalyzer.getInstance();
		 //
		 String sentence = "드론으로 평양까지 대북 전단 1만 장을 보냈다";
		 Vector<String> result = cv.getNouns(sentence);
		 for (int i = 0; i < result.size(); i++)
		 System.out.println(result.get(i));

//		String content = "대구서 마스크 안쓰면 무조건 벌금낸다. \n\t\n대구서 마스크 안쓰면 무조건 벌금낸다.";
//
//		Vector<CompareResult> result2 = cv.sentenceSplitter(content);
//		for (int i = 0; i < result2.size(); i++) {
//			System.out.println(result2.get(i).getSentence());
//			System.out.println(result2.get(i).getPreBlank());
//		}
	}
}
