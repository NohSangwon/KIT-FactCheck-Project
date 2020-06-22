package kr.or.kit.factcheck.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import kr.or.kit.factcheck.dto.Topic;


public class TopicDAO {
	public static void main(String[] args) {
		TopicDAO t = TopicDAO.getInstance();
		t.insertTopic("민주공화국");
	}
	
	private DBConnecter dbConn;
	
    private TopicDAO() {
		dbConn = DBConnecter.getInstance();
    }
 
    private static class LazyHolder {
        public static final TopicDAO INSTANCE = new TopicDAO();
    }
 
    public static TopicDAO getInstance() {
        return LazyHolder.INSTANCE;
    }
	
	public boolean insertTopic(String topic)  {
		Connection conn = dbConn.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT topicID FROM factchecker.topic ORDER BY topicID DESC LIMIT 1;";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int newTopicID = 0;
			if (rs.next()) {
				newTopicID = rs.getInt("topicID") + 1;
			}
			sql = "INSERT INTO topic VALUES (?, ?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newTopicID);
			pstmt.setString(2, topic);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);
		
		return true;
	}

	public Vector<Topic>  getTopicList() {
		Connection conn = dbConn.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Vector<Topic> topics = new Vector<Topic>();
		try {
			String sql = "SELECT * FROM topic;";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("subjectID");
				String topic = rs.getString("topicContent");
				topics.add(new Topic(id, topic));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);
		
		return topics;
	}
	
	public int lastID() {
		Connection conn = dbConn.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int lastId = -1;
		try {
			String sql = "SELECT topicID FROM factchecker.topic ORDER BY topicID DESC LIMIT 1;";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				lastId = rs.getInt("topicID") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbConn.freeConnection(rs);
		dbConn.freeConnection(pstmt);
		dbConn.freeConnection(conn);
		
		return lastId;
	}
}
