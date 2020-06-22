package kr.or.kit.factcheck.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jep.JepException;
import jep.SharedInterpreter;
import kr.or.kit.factcheck.CompareSentenceModule.JepLock;
import kr.or.kit.factcheck.dao.SentenceDAO;

public class SNUCrawlerThread extends Thread {
	JepLock jepLock;
	SentenceDAO sDao;
	boolean executeFlag;
	boolean timeFlag; // True : 실행할 시간이 되었는데 아직 실행 전이다

	public SNUCrawlerThread() {
		jepLock = JepLock.getInstance();
		sDao = SentenceDAO.getInstance();
		executeFlag = true;
		timeFlag = true;
	}

	@Override
	public void run() {
		super.run();

		SharedInterpreter jep = null;
		while (executeFlag) {
			if (timeFlag) {
				synchronized (jepLock) {
					try {
						if (jep == null)
							jep = new SharedInterpreter();
						System.out.println("@SNUCrawlerThread : ---- SNU crawler start ----");

						jep.runScript(
								"C:/Users/aaaaa/workspace/fact-check/BERT_pairwise_text_classification/SNUcrawler.py");

						jep.exec("result = getNewSNUData(1, 5)");

						String result = jep.getValue("result").toString();

						JSONParser p = new JSONParser();
						JSONArray jsonArray = (JSONArray) (p.parse(result));
						System.out.println("@SNUCrawlerThread : " + jsonArray.size());

						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject item = (JSONObject) p.parse(jsonArray.get(i).toString());
							String title = item.get("title").toString();
							String url = item.get("url").toString();
							int score = Integer.parseInt(item.get("score").toString());

							int tfsID = sDao.isExist(title);
							if (tfsID == -1) {
								sDao.insertSentence(title, url, score);
								System.out.println("@SNUCrawlerThread : " + i + " - insert : " + item);
							} else {
								sDao.updateSentence(tfsID, url, score);
								System.out.println("@SNUCrawlerThread : " + i + " - update : " + item);
							}
						}

						timeFlag = false;
						jep.close();
						jep = null;

						System.out.println("@SNUCrawlerThread : ---- SNU crawler complete ----");
					} catch (Exception e) {
						System.out.println("@SNUCrawlerThread : JepException");
						System.out.println("@SNUCrawlerThread : ---- crawler restart ----");
					}

					try {
						if (jep != null) {
							jep.close();
							jep = null;
						}
					} catch (JepException e) {
						e.printStackTrace();
					}
				}
			} else {
				Date currDate = new Date();
				SimpleDateFormat timeFomat = new SimpleDateFormat("HH:mm:ss");
				String currTime = timeFomat.format(currDate);
				if (currTime.equals("03:00:00")) {
					timeFlag = true;
				}
			}
		}
	}

	public void exit() {
		executeFlag = false;
	}

	public static void main(String[] args) {
		Date currDate = new Date();
		int hour = currDate.getHours();
		int min = currDate.getMinutes();
		int sec = currDate.getSeconds();
		SimpleDateFormat timeFomat = new SimpleDateFormat("HH:mm:ss");
		System.out.println(timeFomat.format(currDate));
		// SNUCrawlerThread snuCrawlerThread = new SNUCrawlerThread();
		// snuCrawlerThread.run();
	}
}
