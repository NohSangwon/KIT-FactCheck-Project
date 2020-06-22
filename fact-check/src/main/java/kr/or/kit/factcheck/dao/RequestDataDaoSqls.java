package kr.or.kit.factcheck.dao;

public class RequestDataDaoSqls {
	public static final String SELECT_ALL = "SELECT * FROM requestdata";
	public static final String SELECT_BY_ID = "SELECT * FROM requestdata WHERE id = :id";
	public static final String DELETE_BY_ID = "DELETE FROM requestdata WHERE id = :id";
	public static final String SELECT_COUNT = "SELECT count(*) FROM requestdata";
	public static final String UPDATE_BY_URL = "UPDATE requestdata SET title = :title, content = :content, status = :status WHERE url = :url";
}
