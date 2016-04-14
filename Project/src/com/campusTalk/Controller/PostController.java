package com.campusTalk.Controller;

import java.util.Date;

import org.json.*;

import com.campusTalk.database.*;
import com.campusTalk.model.Post;
import com.campusTalk.model.Subscription;
public class PostController {
	
	private DbProxy dbproxy = null;
	
	public PostController(){
		dbproxy = new DbProxy();
	}

	public void createPost(String postDetails) {
		try {
				JSONObject json = new JSONObject(postDetails);
				int userId = Integer.parseInt(json.getString("userId"));
				int forumId = Integer.parseInt(json.getString("forumId"));
				String postDescription = json.getString("postDescription");
				Date dateCreated = new Date();
				System.out.println("Post Controller createPost - userId, forumId, postDescription, dateCreated "+userId+" "+forumId+" "+postDescription+" "+dateCreated);
				Post post = new Post(1,postDescription,userId,dateCreated,forumId);
				dbproxy.savePostDetails(post);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
}