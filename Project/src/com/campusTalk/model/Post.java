package com.campusTalk.model;

import java.util.Date;

public class Post {
	private int postId;
	private String description;
	private int createdBy;
	private Date createdDate;
	private int forumId;
	
	public Post(){};
	
	public Post(int postId, String description, int createdBy, Date createdDate, int forumId){
		this.postId = postId;
		this.description = description;
		this.createdBy = createdBy;
		if(createdDate == null){
			createdDate = new Date();
			System.out.println("Creation Date null");
			
		}
		this.createdDate = createdDate;
		this.forumId = forumId;
	}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
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
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
}
