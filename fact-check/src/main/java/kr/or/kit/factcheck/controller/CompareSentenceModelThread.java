/**
 * 
 */
package kr.or.kit.factcheck.controller;

import kr.or.kit.factcheck.CompareSentenceModule.CompareSentenceModel;
import kr.or.kit.factcheck.dto.RequestData;

/**
 * @author aaaaa
 * @className RequestThread
 */
public class CompareSentenceModelThread  extends Thread {
	    private CompareSentenceModel csm;
	    private RequestData requestdata;
		
	    public CompareSentenceModelThread(RequestData requestdata){
	    	csm = CompareSentenceModel.getInstance();
	    	this.requestdata = requestdata;
	    }
		
	    @Override
	    public void run() {
			csm.execute(requestdata);
	    }
}
