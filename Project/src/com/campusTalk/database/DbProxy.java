package com.campusTalk.database;

import java.util.List;

import com.campusTalk.model.*;

public class DbProxy implements DbProxyInterface {
	
	private DbHelper dbhelper = null;
	public DbProxy(){
		this.getInstance();
	};

	// Singleton implementation
	public void getInstance(){
		if(this.dbhelper == null){
			this.dbhelper = new DbHelper();
		}
	}
	
	@Override
	public void saveForumDetails(Forum forum) {
		// TODO Auto-generated method stub
		this.dbhelper.saveForumDetails(forum);
	}

	@Override
	public List<Forum> getForumResults(String criteria, int userId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getForumResults(criteria, userId);
	}

	@Override
	public void savePostDetails(Post post) {
		// TODO Auto-generated method stub
		this.dbhelper.savePostDetails(post);
	}

	@Override
	public List<Post> getPostsInForum(int forumId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getPostsInForum(forumId);
	}

	@Override
	public void saveReplyDetails(Reply reply) {
		// TODO Auto-generated method stub
		this.dbhelper.saveReplyDetails(reply);
	}

	@Override
	public List<Reply> getRepliesToPost(int postId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getRepliesToPost(postId);
	}

	@Override
	public void saveSubscription(Subscription subscription) {
		// TODO Auto-generated method stub
		this.dbhelper.saveSubscription(subscription);
	}

	@Override
	public void deleteSubscription(int userId, int forumId) {
		// TODO Auto-generated method stub
		this.dbhelper.deleteSubscription(userId, forumId);
	}
	
	@Override
	public int getCountOfSubscribers(int forumId, int userId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getCountOfSubscribers(forumId, userId);
	}
	
	@Override
	public void saveEventDetails(Event event) {
		// TODO Auto-generated method stub
		this.dbhelper.saveEventDetails(event);
	}

	@Override
	public List<PostAndReply> getPostsAndReplies(int forumId) {
		return this.dbhelper.getPostsAndReplies(forumId);
	}

	@Override
	public User getUser(int userId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getUser(userId);
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return this.dbhelper.getUser(username);
	}
	
	@Override
	public List<Topic> getTopics() {
		// TODO Auto-generated method stub
		return this.dbhelper.getTopics();
	}

	@Override
	public void saveUserDetails(User user) {
		// TODO Auto-generated method stub
		this.dbhelper.saveUserDetails(user);
	}
		
	@Override
	public List<Event> getEventDetails(int userId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getEventDetails(userId);
	}

	@Override
	public List<Forum> getForumsOfaUser(int userId) {
		// TODO Auto-generated method stub
		String criteria = "user";
		return this.dbhelper.getForumsOfaUser(userId);
	}

	@Override
	public void saveUserTopic(UserTopic userTopic) {
		
		// TODO Auto-generated method stub
		this.dbhelper.saveUserTopic(userTopic);
		
	}

	public List<Domain> getDomains() {
		// TODO Auto-generated method stub
		return this.dbhelper.getDomains();
	}

	public List<Area> getAreas(int domainId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getAreas(domainId);
	}

	public List<Topic> getTopics(int areaId) {
		return this.dbhelper.getTopics(areaId);
	}

	public List<Topic> getUserInterests(int userId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getUserInterests(userId);
	}

	public List<Forum> getsearchResults(int topicId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getSearchResults(topicId);
	}

	public List<User> getUserSearchResults(int topicId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getUserSearchResults(topicId);
	}

	public Forum getForumbyId(int forumId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getForumById(forumId);
	}
}