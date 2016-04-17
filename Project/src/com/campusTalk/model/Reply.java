package com.campusTalk.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reply {
	private int replyId;
	private String description;
	private int createdBy;
	private Date createdDate;
	private int postId;
	
	public Reply(){};
	
	public Reply(int replyId, String description, int createdBy, Date createdDate, int postId){
		this.replyId = replyId;
		this.description = description;
		this.createdBy = createdBy;
		if(createdDate == null){
			createdDate = new Date();
		}
		this.createdDate = createdDate;
		this.postId = postId;
	}
	
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
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
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}

}
