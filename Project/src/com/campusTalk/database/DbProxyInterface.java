package com.campusTalk.database;

import java.util.List;

import com.campusTalk.model.*;

public interface DbProxyInterface {
	
	public void saveForumDetails(Forum forum);
	public List<Forum> getForumResults(String criteria,int userId);
	public void savePostDetails(Post post);
	public List<Post> getPostsInForum(int forumId);
	public void saveReplyDetails(Reply reply);
	public List<Reply> getRepliesToPost(int postId);
	public void saveSubscription(Subscription subscription);
	public void deleteSubscription(int userId, int forumId);
	public void saveEventDetails(Event event);	
	public int getCountOfSubscribers(int forumId, int userId);
	public List<PostAndReply> getPostsAndReplies(int forumId);
	public User getUser(int userId);
	public List<Topic> getTopics();
	public void saveUserDetails(User user);
	public List<Forum> getForumsOfaUser(int userId);
	public List<Event> getEventDetails(int userId);
}
