package com.campusTalk.model;

import java.util.List;

public class PostAndReply {
	private Post post;
	private List<Reply> replies;
	String postCreatorName;
	
	public String getPostCreatorName() {
		return postCreatorName;
	}
	public void setPostCreatorName(String postCreatorName) {
		this.postCreatorName = postCreatorName;
	}
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
}
