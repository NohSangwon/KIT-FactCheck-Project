package kr.or.kit.factcheck.CompareSentenceModule;

//jep 사용시 VMArgument에 아래 입력할 것!!! 필수
//-Djava.library.path="C:\Users\aaaaa\AppData\Local\Programs\Python\Python36\Lib\site-packages\jep"

import java.util.Date;
import java.util.HashSet;
import java.util.Vector;
import jep.JepException;
import jep.SharedInterpreter;
import kr.or.kit.factcheck.dao.SentenceDAO;
import kr.or.kit.factcheck.dto.CompareResult;
import kr.or.kit.factcheck.dto.RequestData;
import kr.or.kit.factcheck.dto.Sentence;
import kr.or.kit.factcheck.service.RequestDataService;
import kr.or.kit.factcheck.service.impl.RequestDataServiceImpl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CompareSentenceModel {
	private SentenceAnalyzer sentenceAnalyzer;
	private SentenceDAO sentenceDAO;
	private RequestDataService requestdataService;
	private SharedInterpreter jep;
	private JepLock jepLock;

	private CompareSentenceModel() {
		requestdataService = new RequestDataServiceImpl();
		sentenceAnalyzer = SentenceAnalyzer.getInstance();
		sentenceDAO = SentenceDAO.getInstance();
		jepLock = JepLock.getInstance();
	}

	private static class LazyHolder {
		public static final CompareSentenceModel INSTANCE = new CompareSentenceModel();
	}

	public static CompareSentenceModel getInstance() {
		return LazyHolder.INSTANCE;
	}

	public JSONArray getJArray(Vector<CompareResult> sentenceList) {
		JSONArray jArray = new JSONArray();
		for (int i = 0; i < sentenceList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("sentence", sentenceList.get(i).getSentence());
			jsonObj.put("tfsentence", "NULL");
			jsonObj.put("url", "NULL");
			jsonObj.put("rank", "NULL");
			jsonObj.put("preBlank", sentenceList.get(i).getPreBlank());
			jArray.add(jsonObj);
		}

		return jArray;
	}

	public JSONArray getCompleteJArray(Vector<CompareResult> sentenceList, JSONArray jArray) {
		Vector<CompareResult> crList = new Vector<CompareResult>();
		for (int i = 0; i < sentenceList.size(); i++) {
			crList.add(tfVerification(sentenceList.get(i).getSentence()));
		}
		JSONArray completeTitleJArray = new JSONArray();
		for (int i = 0; i < crList.size(); i++) {
			if (crList.get(i).getCompareResult() == 1 && crList.get(i).getTfsRank() != 0) {
				JSONObject completeTitleJsonObj = new JSONObject();
				completeTitleJsonObj.put("sentence", crList.get(i).getSentence());
				completeTitleJsonObj.put("tfsentence", crList.get(i).getTfSentence());
				completeTitleJsonObj.put("url", crList.get(i).getTfsUrl());
				completeTitleJsonObj.put("rank", crList.get(i).getTfsRank());
				completeTitleJsonObj.put("preBlank", sentenceList.get(i).getPreBlank());
				completeTitleJArray.add(completeTitleJsonObj);
			} else {
				completeTitleJArray.add(jArray.get(i));
			}
		}

		return completeTitleJArray;
	}

	public String execute(RequestData requestdata) {

		synchronized (jepLock) {
			loadModel();
			// requestdataService.addRequestData(requestdata);
			// 입력받은 url, title, content의 문장 유사 모델로 진위여부 판별후 DB에 저장
			// 0 : 판별불가, 1 : 전혀사실아님, 2 : 대체로거짓, 3 : 절반의사실, 4 : 대체로사실, 5 : 매우사실
			// title 진위 검증
			String title = requestdata.getTitle();
			String content = requestdata.getContent();

			Vector<CompareResult> tSentenceList = sentenceAnalyzer.sentenceSplitter(title);
			Vector<CompareResult> cSentenceList = sentenceAnalyzer.sentenceSplitter(content);

			JSONArray titleJArray = getJArray(tSentenceList);
			JSONArray contentJArray = getJArray(cSentenceList);

			// 판별수행

			JSONArray completeTitleJArray = getCompleteJArray(tSentenceList, titleJArray);
			String completeTitleJson = completeTitleJArray.toJSONString();
			System.out.print("@CompareSenteceModel : 판정완료 title - ");
			System.out.println(completeTitleJson);

			JSONArray completeContentJArray = getCompleteJArray(cSentenceList, contentJArray);
			String completeContentJson = completeContentJArray.toJSONString();
			System.out.print("@CompareSenteceModel : 판정완료 content - ");
			System.out.println(completeContentJson);

			requestdata.setTitle(completeTitleJson);
			requestdata.setContent(completeContentJson);
			requestdata.setStatus("판정완료");

			requestdataService.updateRequestData(requestdata);

			closeModel();
		}
		return null;
	}

	public String checkArticleHistory(String url) {
		// 이전에 평가했던 기록이 있는지 DB에서 검사

		return null; // 반환값은 DB 해당 기사 id or 없으면 null
	}

	@SuppressWarnings("deprecation")
	public void loadModel() {
		try {
			jep = new SharedInterpreter();
			jep.exec("import sys");
			jep.exec("sys.path.append('C:/Users/aaaaa/workspace/fact-check/BERT_pairwise_text_classification')");
			jep.runScript("C:/Users/aaaaa/workspace/fact-check/BERT_pairwise_text_classification/predictFunc.py");
		} catch (JepException e) {
			e.printStackTrace();
		}
	}

	public void closeModel() {
		try {
			jep.close();
		} catch (JepException e) {
			e.printStackTrace();
		}
	}

	public String compareSentence(String sentence1, String sentence2) {
		// 두 문장 유사 판별
		String result = "error";
		try {
			jep.exec("sentence1 = '11'");
			jep.exec("sentence2 = '22'");
			jep.set("sentence1", sentence1);
			jep.set("sentence2", sentence2);
			jep.exec("result = predict(sentence1, sentence2)");
			result = jep.getValue("result").toString();
		} catch (JepException e) {
			e.printStackTrace();
		}

		return result;
	}

	public CompareResult tfVerification(String sentence) {
		CompareResult cr = new CompareResult(sentence, "", "", -1, 0, -1, "");

		Vector<String> subject = checkSubject(sentence);
		HashSet<String> subjectSet = new HashSet<String>(subject);

		final int permitGap = 2;
		Vector<Sentence> tfsList = sentenceDAO.getSentence(subject, permitGap);

		if (tfsList == null || tfsList.size() == 0)
			return cr;

		for (int i = 0; i < tfsList.size(); i++) {
			String sentence2 = tfsList.get(i).getSentence();
			String result = compareSentence(sentence, sentence2);

			if (result.equals("1")) {
				cr.setTfSentence(sentence2);
				cr.setTfsUrl(tfsList.get(i).getUrl());
				cr.setTfsID(tfsList.get(i).getTfsID());
				cr.setTfsRank(tfsList.get(i).getRank());
				cr.setCompareResult(1);
				break;
			} else if (result.equals("0")) {
				if (subjectSet.size() == tfsList.get(i).getTopicCnt() && tfsList.get(i).getRank() != 0) {
					switch (tfsList.get(i).getRank()) {
					case 1:
						cr.setTfsRank(5);
						break;
					case 2:
						cr.setTfsRank(4);
						break;
					case 3:
						cr.setTfsRank(3);
						break;
					case 4:
						cr.setTfsRank(2);
						break;
					case 5:
						cr.setTfsRank(1);
						break;
					}
					cr.setTfSentence(sentence2);
					cr.setTfsUrl(tfsList.get(i).getUrl());
					cr.setTfsID(tfsList.get(i).getTfsID());
					cr.setCompareResult(0);
				}
			}
		}

		return cr;
	}

	public Vector<String> checkSubject(String sentence) {
		return sentenceAnalyzer.getNouns(sentence);
	}

	public static void main(String[] args) {
		CompareSentenceModel csm = CompareSentenceModel.getInstance();
		String title = "공손강의 작위는 양평후라고 볼 수 있다.";
		String content = "공손강의 작위는 양평후이다. ";
		content += "구수왕의 이전왕은 초고왕이다. ";
		content += "구수왕의 다음왕은 사반왕이다. ";
		content += "근초공왕의 왕비는 왕비 진씨이다. ";
		content += "비류왕의 다음왕은 계왕이다.";
		RequestData requestData = new RequestData();
		requestData.setUrl("test1");
		requestData.setTitle(title);
		requestData.setContent(content);
		requestData.setDate(new Date());
		requestData.setStatus("판정대기");
		csm.execute(requestData);
	}
}
