package kr.or.kit.factcheck.CompareSentenceModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import kr.or.kit.factcheck.dao.DBConnecter;
import kr.or.kit.factcheck.dto.CompareResult;

public class tmpAccuracy {
	private SentenceAnalyzer sa;
	private CompareSentenceModel csm;
	
	public static void main(String[] args) {
		Vector<String> strList = new Vector<String>();
		Vector<Integer> rankList = new Vector<Integer>();
		tmpAccuracy ta = new tmpAccuracy();
		ta.dataLoadFromFile(strList, rankList, "test2.txt");
		
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int f = 0;
		for (int i = 0; i < rankList.size(); i++) {
			switch (rankList.get(i)) {
			case 1: b++; break;
			case 2: c++; break;
			case 3: d++; break;
			case 4: e++; break;
			case 5: f++; break;
			}
		}
		System.out.println("--- 문장 갯수 ---");
		System.out.println("rank:1 - " + b);
		System.out.println("rank:2 - " + c);
		System.out.println("rank:3 - " + d);
		System.out.println("rank:4 - " + e);
		System.out.println("rank:5 - " + f);
		System.out.println("------------");
		float acc = ta.accuracy(strList, rankList, "test3_result.txt");
		System.out.println(acc);
		
	}

	public tmpAccuracy() {
		sa = SentenceAnalyzer.getInstance();
		csm = CompareSentenceModel.getInstance();
	}
	
	public void getTFS() {
		DBConnecter dbConn = DBConnecter.getInstance();
		Connection conn = dbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<String> sList = new Vector<>();
		Vector<Integer> rList = new Vector<>();
		
		int tfsID = -1;
		
		try {
			String sql = "SELECT * FROM factchecker.tfsentence WHERE tfsID >= ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 749942);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String str = rs.getString("sentence");
				int rank = rs.getInt("rank");
				sList.add(str);
				rList.add(rank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);
		
		for (int i = 0; i < sList.size(); i++) {
			File file = new File("C:/Users/aaaaa/workspace/fact-check/src/test/java/test2.txt");

			try {
				FileWriter fw = new FileWriter(file, true);
				fw.write(sList.get(i));
				fw.write("|");
				fw.write(Integer.toString(rList.get(i)));
				fw.write("\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public float accuracy(Vector<String> sList, Vector<Integer> rList, String filename) {

		float acc = 0;
		synchronized (csm) {
			csm.loadModel();
			int b = 0;
			int c = 0;
			int d = 0;
			int e = 0;
			int f = 0;

			File file = new File("C:/Users/aaaaa/workspace/fact-check/src/test/java/" + filename);
			
			int size = sList.size();
			for (int i = 0; i < size; i++) {
				CompareResult result = csm.tfVerification(sList.get(i));
				if (rList.get(i) == result.getTfsRank()) {
					switch (rList.get(i)) {
					case 1: b++; break;
					case 2: c++; break;
					case 3: d++; break;
					case 4: e++; break;
					case 5: f++; break;
					}
				}			

				String tmp = i + "|" + result.getSentence() + "|" + result.getTfSentence() + "|" + rList.get(i) + "|" + result.getTfsRank();
				try {
					FileWriter fw = new FileWriter(file, true);
					fw.write(tmp);
					fw.write("\n");
					fw.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				System.out.println(tmp);
			}
			System.out.println("맞춘 갯수");
			System.out.println("rank:1 - " + b);
			System.out.println("rank:2 - " + c);
			System.out.println("rank:3 - " + d);
			System.out.println("rank:4 - " + e);
			System.out.println("rank:5 - " + f);
			System.out.println("------------");
			float sum = b + c + d + e + f;
			acc = sum/size;
			csm.closeModel();
		}
		
		return acc;
	}

	public void dataLoadFromFile(Vector<String> strList, Vector<Integer> rankList, String fileName) {
		File file = new File("C:/Users/aaaaa/workspace/fact-check/src/test/java/" + fileName);
		try {
			if (file.exists()) {
				FileInputStream fileInputStream;
				fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String sLine = null;
				while ((sLine = bufferedReader.readLine()) != null) {
					String item[] = sLine.split("\\|");
					strList.add(item[0]);
					rankList.add(Integer.parseInt(item[1]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dataToFile(String title, String content, String rank) {
		Vector<CompareResult> tSentenceList = sa.sentenceSplitter(title);
		Vector<CompareResult> cSentenceList = sa.sentenceSplitter(content);

		for (int i = 0; i < tSentenceList.size(); i++) {
			File file = new File("C:/Users/aaaaa/workspace/fact-check/src/test/java/test1.txt");

			try {
				// 파일에 문자열을 쓴다
				// 하지만 파일이 존재한다면 덮어쓰는게 아니라 .
				// 그 뒤에 문자열을 이어서 작성한다.
				FileWriter fw = new FileWriter(file, true);
				fw.write(tSentenceList.get(i).getSentence());
				System.out.println(tSentenceList.get(i).getSentence());
				fw.write("|");
				fw.write(rank);
				fw.write("\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < cSentenceList.size(); i++) {
			File file = new File("C:/Users/aaaaa/workspace/fact-check/src/test/java/test1.txt");

			try {
				FileWriter fw = new FileWriter(file, true);
				fw.write(cSentenceList.get(i).getSentence());
				System.out.println(cSentenceList.get(i).getSentence());
				fw.write("|");
				fw.write(rank);
				fw.write("\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
