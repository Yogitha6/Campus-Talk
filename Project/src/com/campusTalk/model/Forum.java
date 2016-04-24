package com.campusTalk.model;

import java.util.*;

public class Forum {
	private int forumId;
	private String description;
	private int createdBy;
	private Date createdDate;
	private int topicId;
	
	public Forum(){};
	
	public Forum(String description, int createdBy, Date createdDate, int topicId){
		this.description = description;
		this.createdBy = createdBy;
		if(createdDate == null){
			createdDate = new Date();	
		}
		this.createdDate = createdDate;
		this.topicId = topicId;
	}
	
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
}
