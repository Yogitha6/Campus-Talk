package com.campusTalk.Controller;

import java.util.*;
import org.json.*;
import com.campusTalk.database.*;
import com.campusTalk.model.*;

public class ForumController {
	
	private DbProxy dbproxy = null;
	
	public ForumController(){
		dbproxy = new DbProxy();
	}

	public void unsubscribe(String userForumDetails) {
		try {
				JSONObject json = new JSONObject(userForumDetails);
				int userId = Integer.parseInt(json.getString("userId"));
				int forumId = Integer.parseInt(json.getString("forumId"));
				//System.out.println("Forum Controller unsubscribe - userId and forumId "+userId+" "+forumId);
				dbproxy.deleteSubscription(userId, forumId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void subscribe(String userForumDetails) {
		try {
				JSONObject json = new JSONObject(userForumDetails);
				int userId = Integer.parseInt(json.getString("userId"));
				int forumId = Integer.parseInt(json.getString("forumId"));
				Subscription subscription = new Subscription(userId,forumId);
				//System.out.println("Forum Controller subscribe - userId and forumId "+userId+" "+forumId);
				dbproxy.saveSubscription(subscription);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void createForum(String forumDetails) {
		try {
				JSONObject json = new JSONObject(forumDetails);
				int userId = Integer.parseInt(json.getString("userId"));
				int topicId = Integer.parseInt(json.getString("topicId"));
				String forumDescription = json.getString("forumDescription");
				Date dateCreated = new Date();
				//System.out.println("Forum Controller createForum - userId, topicId, forumDescription, dateCreated "+userId+" "+topicId+" "+forumDescription+" "+dateCreated);
				Forum forum = new Forum(1,forumDescription,userId,dateCreated,topicId);
				dbproxy.saveForumDetails(forum);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public List<Post> getPostsInForum(String forumIdJSON) {
		List<Post> posts = new ArrayList<Post>();
		try {
				JSONObject json = new JSONObject(forumIdJSON);
				int forumId = Integer.parseInt(json.getString("forumId"));
				//System.out.println("Forum Controller getPostsInForum - forumId "+forumId);
				posts = dbproxy.getPostsInForum(forumId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return posts;
	}
	
	public List<PostAndReply> getPostsAndReplies(String forumIdJSON) {
		List<PostAndReply> prArr = new ArrayList<PostAndReply>();
		try {
				JSONObject json = new JSONObject(forumIdJSON);
				int forumId = Integer.parseInt(json.getString("forumId"));
				//System.out.println("Forum Controller getPostsInForum - forumId "+forumId);
				prArr = dbproxy.getPostsAndReplies(forumId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return prArr;
	}
	
	public int getCountOfSubscribers(String forumIdJSON) {
		int count = 0;
		try {
				JSONObject json = new JSONObject(forumIdJSON);
				int forumId = Integer.parseInt(json.getString("forumId"));
				int userId = Integer.parseInt(json.getString("userId"));
				//System.out.println("Forum Controller getCountOfSubscribers - forumId "+forumId+",userId - "+userId);
				count = dbproxy.getCountOfSubscribers(forumId, userId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<Topic> getTopics() {
		List<Topic> topicArr = new ArrayList<Topic>();
		try {
				topicArr = dbproxy.getTopics();
			} catch (Exception e) {
			e.printStackTrace();
		}
		return topicArr;
	}
	
	public List<Domain> getDomain() {
		List<Domain> domainArr = new ArrayList<Domain>();
		try {
				domainArr = dbproxy.getDomains();
			} catch (Exception e) {
			e.printStackTrace();
		}
		return domainArr;
	}

	public List<Area> getArea(int domainId) {
		List<Area> areaArr = new ArrayList<Area>();
		try {
				areaArr = dbproxy.getAreas( domainId );
			} catch (Exception e) {
			e.printStackTrace();
		}
		return areaArr;
	}

	public List<Topic> getTopic(int areaId) {
		List<Topic> topicArr = new ArrayList<Topic>();
		try {
				topicArr = dbproxy.getTopics( areaId );
			} catch (Exception e) {
			e.printStackTrace();
		}
		return topicArr;
	}
	
}