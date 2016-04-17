package com.campusTalk.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.*;

import com.campusTalk.database.*;
import com.campusTalk.model.*;

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
				//System.out.println("Post Controller createPost - userId, forumId, postDescription, dateCreated "+userId+" "+forumId+" "+postDescription+" "+dateCreated);
				Post post = new Post(1,postDescription,userId,dateCreated,forumId);
				dbproxy.savePostDetails(post);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public List<Reply> getRepliesToPost(String postIdJSON) {
		List<Reply> replies = new ArrayList<Reply>();
		try {
				JSONObject json = new JSONObject(postIdJSON);
				int postId = Integer.parseInt(json.getString("postId"));
				//System.out.println("Post Controller getRepliesToPost - postId "+postId);
				replies = dbproxy.getRepliesToPost(postId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return replies;
	}
	
}