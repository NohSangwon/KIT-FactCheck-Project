package kr.or.kit.factcheck.dao;

import static kr.or.kit.factcheck.dao.RequestDataDaoSqls.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.kit.factcheck.dto.RequestData;

@Repository
public class RequestDataDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<RequestData> rowMapper = BeanPropertyRowMapper.newInstance(RequestData.class);

	public RequestDataDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("requestdata")
				.usingGeneratedKeyColumns("id");
	}

	public List<RequestData> selectAll() {
		return jdbc.query(SELECT_ALL, rowMapper);
	}

	public List<RequestData> selectById(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.query(SELECT_BY_ID, params, rowMapper);
	}

	public int insert(RequestData requestdata) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(requestdata);
		return (int) insertAction.executeAndReturnKey(params).longValue();
	}

	public int deleteById(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(DELETE_BY_ID, params);
	}

	public int selectCount() {
		return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
	}

	public int updateRequestData(RequestData requestData) {
		Map<String, String> params = new HashMap<>();
		params.put("title", requestData.getTitle());
		params.put("content", requestData.getContent());
		params.put("status", requestData.getStatus());
		params.put("url", requestData.getUrl());
		return jdbc.update(UPDATE_BY_URL, params);
	}
}
