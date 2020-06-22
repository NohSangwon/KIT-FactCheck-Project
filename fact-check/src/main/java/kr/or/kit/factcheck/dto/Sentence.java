package kr.or.kit.factcheck.dto;

import java.util.Comparator;

public class Sentence {
	
	private int tfsID;
	private String sentence;
	private String url;
	private int rank;
	private int score;
	private int topicCnt;
	
	public Sentence() {
		this.tfsID = -1;
		this.sentence = "";
		this.url = "";
		this.rank = 0;
		this.score = 0;
		this.topicCnt = 0;
	}
	
	public Sentence(int tfsID, String sentence, String url, int rank, int score, int topicCnt) {
		this.tfsID = tfsID;
		this.sentence = sentence;
		this.url = url;
		this.rank = rank;
		this.score = score;
		this.topicCnt = topicCnt;
	}


	public int getTfsID() {
		return tfsID;
	}

	public String getSentence() {
		return sentence;
	}
	
	public String getUrl() {
		return url;
	}

	public int getRank() {
		return rank;
	}

	public int getScore() {
		return score;
	}
	
	public int getTopicCnt() {
		return topicCnt;
	}

	public void setTfsID(int tfsID) {
		this.tfsID = tfsID;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void setTopicCnt(int topicCnt) {
		this.topicCnt = topicCnt;
	}
	
	public static SentenceComparator getComparator() {
		return SentenceComparator.getInstance();
	}
	
	private static class SentenceComparator implements Comparator {
	    private SentenceComparator() {
	    }
	 
	    private static class LazyHolder {
	        public static final SentenceComparator INSTANCE = new SentenceComparator();
	    }
	 
	    public static SentenceComparator getInstance() {
	        return LazyHolder.INSTANCE;
	    }
		@Override
		public int compare(Object o1, Object o2) {
			return ((Sentence)o1).getScore() > ((Sentence)o2).getScore() ? -1 : (o1 == o2 ? 0 : 1);
		}
	}
}
