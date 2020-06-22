package kr.or.kit.factcheck.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.kit.factcheck.service.RequestDataService;
import kr.or.kit.factcheck.CompareSentenceModule.SentenceAnalyzer;
import kr.or.kit.factcheck.dto.CompareResult;
import kr.or.kit.factcheck.dto.RequestData;

@Controller
public class RequestDataController {
	@Autowired
	RequestDataService requestdataService;
	
	
	   @GetMapping(path = "/list")
	   public void list(ModelMap model) {

	      List<RequestData> list = requestdataService.getRequestDatas();
	      ArrayList<JSONArray> tlist = new ArrayList<>();
	      for(int i=0;i<list.size();i++) {
	        JSONArray titleArray = null;
			try {
				titleArray = new JSONArray(list.get(i).getTitle());
			} catch (JSONException e) {
				e.printStackTrace();
			}
	         tlist.add(titleArray);
	      }
	      model.addAttribute("list", list);
	      model.addAttribute("tlist", tlist);
	   }

	
	   @RequestMapping(path = "/result", method = RequestMethod.GET)
	   public void result(@RequestParam("id") int id, ModelMap model) {
	      List<RequestData> list = requestdataService.getRequestDataById(id);
	      RequestData result = list.get(0);
	      String str = result.getContent();
	      str = str.replaceAll("\\\\r\\\\n", "<br>");
	      JSONArray titleArray = null;
	      JSONArray contentArray = null;
		try {
			titleArray = new JSONArray(result.getTitle());
		    contentArray = new JSONArray(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	      model.addAttribute("result", result);
	      model.addAttribute("titleArray", titleArray);
	      model.addAttribute("contentArray", contentArray);
	   }
	
	@RequestMapping(path="/register", method=RequestMethod.GET)
	public String userform() {
		return "register";
	}
	
	
	
	@PostMapping(path="/write")
	public String write(@ModelAttribute RequestData requestdata,
						HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		System.out.println("clientIp : " + clientIp);
//		tmpAccuracy tmpAccuracy = new tmpAccuracy();
//		tmpAccuracy.dataToFile(requestdata.getTitle(), requestdata.getContent(), requestdata.getUser());
		
		SentenceAnalyzer sa = SentenceAnalyzer.getInstance();
		Vector<CompareResult> tSentenceList = sa.sentenceSplitter(requestdata.getTitle());
		Vector<CompareResult> cSentenceList = sa.sentenceSplitter(requestdata.getContent());
	
		
		org.json.simple.JSONArray tJArray = new org.json.simple.JSONArray();
		for (int i = 0; i < tSentenceList.size(); i++) {
			org.json.simple.JSONObject jsonObj = new org.json.simple.JSONObject();
			jsonObj.put("sentence", tSentenceList.get(i).getSentence());
			jsonObj.put("tfsentence", "NULL");
			jsonObj.put("url", "NULL");
			jsonObj.put("rank", "NULL");
			jsonObj.put("preBlank", tSentenceList.get(i).getPreBlank());
			tJArray.add(jsonObj);
		}
		
		org.json.simple.JSONArray cJArray = new org.json.simple.JSONArray();
		for (int i = 0; i < cSentenceList.size(); i++) {
			org.json.simple.JSONObject jsonObj = new org.json.simple.JSONObject();
			jsonObj.put("sentence", cSentenceList.get(i).getSentence());
			jsonObj.put("tfsentence", "NULL");
			jsonObj.put("url", "NULL");
			jsonObj.put("rank", "NULL");
			jsonObj.put("preBlank", cSentenceList.get(i).getPreBlank());
			cJArray.add(jsonObj);
		}
		
		String titleJson = tJArray.toJSONString();
		String contentJson = cJArray.toJSONString();

		System.out.print("@RequestDataController : 요청된 title - ");
		System.out.println(titleJson);

		System.out.print("@RequestDataController : 요청된 content - ");
		System.out.println(contentJson);
		
		RequestData tmpRequestData = new RequestData();
		tmpRequestData.setContent(contentJson);
		tmpRequestData.setDate(requestdata.getDate());
		tmpRequestData.setId(requestdata.getId());
		tmpRequestData.setStatus(requestdata.getStatus());
		tmpRequestData.setTitle(titleJson);
		tmpRequestData.setUrl(requestdata.getUrl());
		tmpRequestData.setUser(requestdata.getUser());
		requestdataService.addRequestData(tmpRequestData);
		
		
		CompareSentenceModelThread csmThread = new CompareSentenceModelThread(requestdata);
		csmThread.start();

		return "redirect:list";
	}
}
