package kr.or.kit.factcheck.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kit.factcheck.service.RequestDataService;
import kr.or.kit.factcheck.config.DBConfig;
import kr.or.kit.factcheck.dao.RequestDataDao;
import kr.or.kit.factcheck.dto.RequestData;
import kr.or.kit.factcheck.service.RequestDataService;

@Service
public class RequestDataServiceImpl implements RequestDataService{
	@Autowired
	RequestDataDao requestdataDao;

	public RequestDataServiceImpl() {
		DBConfig dbConfig = new DBConfig();
		requestdataDao = new RequestDataDao(dbConfig.dataSource());
	}
	@Override
	@Transactional
	public List<RequestData> getRequestDatas() {
		List<RequestData> list = requestdataDao.selectAll();
		return list;
	}

	@Override
	public List<RequestData> getRequestDataById(int id) {
		List<RequestData> result = requestdataDao.selectById(id);
		return result;
	}
	
	@Override
	@Transactional(readOnly=false)
	public int deleteRequestData(int id) {
		int deleteCount = requestdataDao.deleteById(id);
		return deleteCount;
	}

	@Override
	@Transactional(readOnly=false)
	public RequestData addRequestData(RequestData requestdata) {
		requestdata.setDate(new Date());
		requestdata.setStatus("판정대기");
		int id = requestdataDao.insert(requestdata);
		requestdata.setId(id);
	
		return requestdata;
	}

	@Override
	public int updateRequestData(RequestData requestData) {
		return requestdataDao.updateRequestData(requestData);
	}
}
