package kr.or.kit.factcheck.dto;

public class CompareResult {
	private String sentence;
	private String tfSentence;
	private String tfsUrl;
	private int tfsID;
	private int tfsRank;
	private int compareResult;	// 문장 비교 결과 0, 1
	private String preBlank;

	public CompareResult() {
		this.sentence = "";
		this.tfSentence = "";
		this.tfsUrl = "";
		this.tfsID = -1;
		this.tfsRank = 0;
		this.compareResult = -1;
		this.preBlank = "";
	}
	
	public CompareResult(String sentence, String tfSentence, String tfsUrl, int tfsID, int tfsRank, int compareResult, String preBlank) {
		this.sentence = sentence;
		this.tfSentence = tfSentence;
		this.tfsUrl = tfsUrl;
		this.tfsID = tfsID;
		this.tfsRank = tfsRank;
		this.compareResult = compareResult;
		this.preBlank = preBlank;
	}
	
	public String getSentence() {
		return sentence;
	}

	public String getTfSentence() {
		return tfSentence;
	}
	
	public String getTfsUrl() {
		return tfsUrl;
	}

	public int getTfsID() {
		return tfsID;
	}

	public int getTfsRank() {
		return tfsRank;
	}
	
	public int getCompareResult() {
		return compareResult;
	}

	public String getPreBlank() {
		return preBlank;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public void setTfSentence(String tfSentence) {
		this.tfSentence = tfSentence;
	}

	public void setTfsUrl(String tfsUrl) {
		this.tfsUrl = tfsUrl;
	}
	
	public void setTfsID(int tfsID) {
		this.tfsID = tfsID;
	}

	public void setTfsRank(int tfsRank) {
		this.tfsRank = tfsRank;
	}

	public void setCompareResult(int compareResult) {
		this.compareResult = compareResult;
	}
	
	public void setPreBlank(String preBlank) {
		this.preBlank = preBlank;
	}
}
