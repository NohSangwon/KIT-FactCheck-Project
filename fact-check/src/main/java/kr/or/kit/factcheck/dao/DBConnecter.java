package kr.or.kit.factcheck.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnecter {
	private static final String FOR_NAME = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/factchecker?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC";
	private static final String ID = "root"; 
	private static final String PASSWORD = "1346";

    private DBConnecter() { }
 
    private static class LazyHolder {
        public static final DBConnecter INSTANCE = new DBConnecter();
    }
 
    public static DBConnecter getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(FOR_NAME); 
            con = DriverManager.getConnection(URL, ID, PASSWORD);

        } catch (Exception e) {			
            e.printStackTrace();
        }
        
        return con;
    }

	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
	    try {
	        if (rs != null) rs.close();
	        if (pstmt != null) pstmt.close();
	        freeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void freeConnection(Connection con, Statement stmt, ResultSet rs) {
	    try {
	        if (rs != null) rs.close();
	        if (stmt != null) stmt.close();
	        freeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void freeConnection(Connection con, PreparedStatement pstmt) {
	    try {
	        if (pstmt != null) pstmt.close();
	        freeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void freeConnection(Connection con, Statement stmt) {
	    try {
	        if (stmt != null) stmt.close();
	        freeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void freeConnection(Connection con) {
	    try {
	        if (con != null) con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void freeConnection(Statement stmt) {
	    try {
	        if (stmt != null) stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void freeConnection(PreparedStatement pstmt) {
	    try {
	        if (pstmt != null) pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void freeConnection(ResultSet rs) {
	    try {
	        if (rs != null) rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
