package com.campusTalk.model;

public class UserTopic {
	private int id;
	private int userId;
	private int topicId;
	
	public UserTopic(){};
	
	public UserTopic(int userId, int topicId){
		this.userId = userId;
		this.topicId = topicId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

}
