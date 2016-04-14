package com.campusTalk.Controller;

import java.util.Date;

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
				System.out.println("Forum Controller unsubscribe - userId and forumId "+userId+" "+forumId);
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
				System.out.println("Forum Controller subscribe - userId and forumId "+userId+" "+forumId);
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
				System.out.println("Forum Controller createForum - userId, topicId, forumDescription, dateCreated "+userId+" "+topicId+" "+forumDescription+" "+dateCreated);
				Forum forum = new Forum(1,forumDescription,userId,dateCreated,topicId);
				dbproxy.saveForumDetails(forum);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}