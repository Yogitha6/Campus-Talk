package com.campusTalk.Controller;

import org.json.*;

import com.campusTalk.database.*;
import com.campusTalk.model.Subscription;
public class ForumController {
	
	private DbProxy dbproxy = null;
	
	public ForumController(){
		dbproxy = new DbProxy();
	}

	public void unsubscribe(String userDetails) {
		try {
				JSONObject json = new JSONObject(userDetails);
				int userId = Integer.parseInt(json.getString("userId"));
				int forumId = Integer.parseInt(json.getString("forumId"));
				System.out.println("Forum Controller unsubscribe - userId and forumId "+userId+" "+forumId);
				dbproxy.deleteSubscription(userId, forumId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void subscribe(String userDetails) {
		try {
				JSONObject json = new JSONObject(userDetails);
				int userId = Integer.parseInt(json.getString("userId"));
				int forumId = Integer.parseInt(json.getString("forumId"));
				Subscription subscription = new Subscription(userId,forumId);
				System.out.println("Forum Controller subscribe - userId and forumId "+userId+" "+forumId);
				dbproxy.saveSubscription(subscription);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}