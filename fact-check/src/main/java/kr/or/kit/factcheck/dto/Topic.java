package kr.or.kit.factcheck.dto;

public class Topic {
	private int topicID;
	private String topic;
	
	public Topic() {
		this.topicID = -1;
		this.topic = "";
	}
	
	public Topic(int topicID, String topic) {
		this.topicID = topicID;
		this.topic = topic;
	}
	
	public int getTopicID() {
		return topicID;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic_id(int topicID) {
		this.topicID = topicID;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
}
