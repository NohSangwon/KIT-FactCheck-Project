package kr.or.kit.factcheck.service;

import java.util.List;

import kr.or.kit.factcheck.dto.RequestData;

public interface RequestDataService {
	public List<RequestData> getRequestDatas();
	public List<RequestData> getRequestDataById(int id);
	public int deleteRequestData(int id);
	public RequestData addRequestData(RequestData requestdata);
	public int updateRequestData(RequestData requestData);
}
