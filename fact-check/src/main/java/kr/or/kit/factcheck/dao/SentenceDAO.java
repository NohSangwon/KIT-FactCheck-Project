package kr.or.kit.factcheck.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import kr.or.kit.factcheck.CompareSentenceModule.SentenceAnalyzer;
import kr.or.kit.factcheck.dto.Sentence;
import scala.annotation.meta.setter;

public class SentenceDAO {
	public static void main(String[] args) {
		SentenceDAO sentenceDAO = SentenceDAO.getInstance();
		sentenceDAO.insertSentence();
		// // sentenceDAO.insertSentence("대한민국의 수도에는 경복궁이 존재한다.", "url", 5);
		// SentenceAnalyzer sa = SentenceAnalyzer.getInstance();
		// Vector<Sentence> tfsList = sentenceDAO.getSentence(sa.getNouns("공손강의
		// 작위는 양평후이다."), 1);
		// for (int i = 0; i < tfsList.size(); i++) {
		// System.out.println(tfsList.get(i).getSentence());
		// }
	}

	private DBConnecter dbConn;
	private SentenceAnalyzer sa;
	private TopicDAO topicDAO;

	private SentenceDAO() {
		dbConn = DBConnecter.getInstance();
		sa = SentenceAnalyzer.getInstance();
		topicDAO = TopicDAO.getInstance();
	}

	private static class LazyHolder {
		public static final SentenceDAO INSTANCE = new SentenceDAO();
	}

	public static SentenceDAO getInstance() {
		return LazyHolder.INSTANCE;
	}
	

	public int isExist(String sentence) {
		Connection conn = dbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int tfsID = -1;
		
		try {
			String sql = "SELECT tfsentence.tfsID FROM factchecker.tfsentence WHERE tfsentence.sentence = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sentence);
			rs = pstmt.executeQuery();
			
			if (rs.next())
				tfsID = rs.getInt("tfsID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);
		return tfsID;
	}
	
	public void updateSentence(int tfsID, String url, int rank) {
		Connection conn = dbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "UPDATE tfsentence SET tfsentence.url = ?, tfsentence.rank = ? WHERE tfsentence.tfsID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, url);
			pstmt.setInt(2, rank);
			pstmt.setInt(3, tfsID);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);
	}

	public void insertSentence() {

		Connection conn = dbConn.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM factchecker.tfsentence WHERE tfsentence.topicCnt = 0;";
			Vector<Sentence> sList = new Vector<Sentence>();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				sList.add(new Sentence(rs.getInt("tfsID"), rs.getString("sentence"), rs.getString("url"),
						rs.getInt("rank"), 0, rs.getInt("topicCnt")));
			}
			dbConn.freeConnection(rs);

			System.out.println(sList.size());
			if (sList.size() == 0)
				return;

			for (int i = 0; i < sList.size(); i++) {
				Vector<String> nouns = sa.getNouns(sList.get(i).getSentence());

				sql = "UPDATE tfsentence SET tfsentence.topicCnt = ? WHERE tfsentence.tfsID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, nouns.size());
				pstmt.setInt(2, sList.get(i).getTfsID());
				pstmt.executeUpdate();
				dbConn.freeConnection(pstmt);

				for (int j = 0; j < nouns.size(); j++) {
					sql = "SELECT * FROM topic WHERE topicContent = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, nouns.get(j));
					rs = pstmt.executeQuery();

					int isExist = 0;
					int includedTopicID = -1;
					if (rs.next()) {
						includedTopicID = rs.getInt("topicID");
						isExist = 1;
					}
					dbConn.freeConnection(rs);
					dbConn.freeConnection(pstmt);

					sql = "SELECT tisID FROM factchecker.topicincludedsentence ORDER BY tisID DESC LIMIT 1;";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);

					int newIncludedID = 0;
					if (rs.next()) {
						newIncludedID = rs.getInt("tisID") + 1;
					}
					if (isExist == 0) {
						topicDAO.insertTopic(nouns.get(j));
						int newTopicID = topicDAO.lastID() - 1;
						String sql2 = "INSERT INTO topicincludedsentence VALUE (?, ?, ?);";
						pstmt = conn.prepareStatement(sql2);
						pstmt.setInt(1, newIncludedID);
						pstmt.setInt(2, newTopicID);
						pstmt.setInt(3, sList.get(i).getTfsID());
					} else if (isExist == 1) {
						String sql2 = "INSERT INTO topicincludedsentence VALUE (?, ?, ?);";
						pstmt = conn.prepareStatement(sql2);
						pstmt.setInt(1, newIncludedID);
						pstmt.setInt(2, includedTopicID);
						pstmt.setInt(3, sList.get(i).getTfsID());
					}
					pstmt.executeUpdate();
				}
				dbConn.freeConnection(rs);
				dbConn.freeConnection(pstmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(stmt);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);
	}

	@SuppressWarnings("resource")
	public boolean insertSentence(String sentence, String url, int rank) {
		Vector<String> nouns = sa.getNouns(sentence);

		Connection conn = dbConn.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT tfsID FROM factchecker.tfsentence ORDER BY tfsID DESC LIMIT 1;";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int newTFSID = 0;
			if (rs.next()) {
				newTFSID = rs.getInt("tfsID") + 1;
			}
			sql = "INSERT INTO tfsentence VALUE (?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newTFSID);
			pstmt.setString(2, sentence);
			pstmt.setString(3, url);
			pstmt.setInt(4, rank);
			pstmt.setInt(5, nouns.size());
			pstmt.executeUpdate();
			dbConn.freeConnection(pstmt);

			for (int i = 0; i < nouns.size(); i++) {
				sql = "SELECT * FROM topic WHERE topicContent = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, nouns.get(i));
				rs = pstmt.executeQuery();

				int isExist = 0;
				int includedTopicID = -1;
				if (rs.next()) {
					includedTopicID = rs.getInt("topicID");
					isExist = 1;
				}
				dbConn.freeConnection(pstmt);

				sql = "SELECT tisID FROM factchecker.topicincludedsentence ORDER BY tisID DESC LIMIT 1;";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				int newIncludedID = 0;
				if (rs.next()) {
					newIncludedID = rs.getInt("tisID") + 1;
				}
				if (isExist == 0) {
					topicDAO.insertTopic(nouns.get(i));
					int newTopicID = topicDAO.lastID() - 1;
					String sql2 = "INSERT INTO topicincludedsentence VALUE (?, ?, ?);";
					pstmt = conn.prepareStatement(sql2);
					pstmt.setInt(1, newIncludedID);
					pstmt.setInt(2, newTopicID);
					pstmt.setInt(3, newTFSID);
				} else if (isExist == 1) {
					String sql2 = "INSERT INTO topicincludedsentence VALUE (?, ?, ?);";
					pstmt = conn.prepareStatement(sql2);
					pstmt.setInt(1, newIncludedID);
					pstmt.setInt(2, includedTopicID);
					pstmt.setInt(3, newTFSID);
				}
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(stmt);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);

		return false;
	}

	// permitGap : 입력 문장의 topic들과 비교문장이 가지는 일치 topic 갯수의 차이 허용값
	public Vector<Sentence> getSentence(Vector<String> subject, int permitGap) {
		if (subject.size() == 0)
			return null;

		Vector<Sentence> result = new Vector<Sentence>();
		HashSet<String> subjectSet = new HashSet<String>(subject);

		Connection conn = dbConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			int sjsSize = subjectSet.size();
			// -- 토픽을 포함하는 tfsentece + cnt(동일 토픽출현 갯수) 를 cnt기준으로 DESC로 가져오기 --
			String sql = "SELECT tfsentence.tfsID, tfsentence.sentence, tfsentence.url, tfsentence.rank, tfsentence.topicCnt, tmp2.cnt ";
			sql += "FROM (SELECT tmp.tfsID, count(*) AS cnt ";
			sql += "FROM (SELECT DISTINCT topic.topicID, topicincludedsentence.tfsID ";
			sql += "FROM topic JOIN topicincludedsentence ";
			sql += "ON topic.topicID = topicincludedsentence.topicID AND ";
			sql += "(topic.topicContent = ?";
			for (int i = 1; i < sjsSize; i++) {
				sql += " OR topic.topicContent = ?";
			}
			sql += ")) AS tmp GROUP BY tmp.tfsID HAVING COUNT(*) > ?) AS tmp2 ";
			sql += "JOIN tfsentence ON tmp2.tfsID = tfsentence.tfsID AND tfsentence.topicCnt < ? ORDER BY tmp2.cnt DESC;";

			pstmt = conn.prepareStatement(sql);
			Iterator<String> iter = subjectSet.iterator(); // 반복자 생성

			for (int i = 0; iter.hasNext(); i++) {
				String topic = iter.next(); // set에 저장된 다음 객체의 참조값 저장
				pstmt.setString(i + 1, topic);
			}
			pstmt.setInt(sjsSize + 1, sjsSize - permitGap); // 동일 토픽이 (입력 문장
															// topic 갯수 -
															// permitGap) 개 이상
															// 포함하는 문장만 추출
			pstmt.setInt(sjsSize + 2, sjsSize + permitGap); // 총 토픽갯수가 (입력 문장
															// topic 갯수 +
															// permitGap)개 이하
															// 포함하는 문장만 추출

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(new Sentence(rs.getInt("tfsID"), rs.getString("sentence"), rs.getString("url"),
						rs.getInt("rank"), rs.getInt("cnt"), rs.getInt("topicCnt")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);

		return result;
	}
}
