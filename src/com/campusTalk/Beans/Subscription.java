package model;

public class Subscription {
	private int subscriptionId;
	private int userId;
	private int forumId;
	
	public Subscription(){};
	
	public Subscription(int userId, int forumId){
		this.userId = userId;
		this.forumId = forumId;
	}
	
	public int getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
}
