package com.campusTalk.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.campusTalk.database.DbProxy;
import com.campusTalk.model.Event;
import com.campusTalk.model.Forum;
import com.campusTalk.model.Post;

public class HomePageController {
	
private DbProxy dbproxy = null;
	
	public HomePageController(){
		dbproxy = new DbProxy();
	}
	
	public List<Forum> getHomePageForumMemberships(String userIdJSON) {
		List<Forum> forums = new ArrayList<Forum>();
		try {
			    //System.out.println(userIdJSON);
				JSONObject json = new JSONObject(userIdJSON);
				int userId = Integer.parseInt(json.getString("userId"));
				forums = dbproxy.getForumsOfaUser(userId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return forums;
	}

	public List<Event> getHomePageEvents(String userIdJSON) {
		// TODO Auto-generated method stub
		List<Event> events = new ArrayList<Event>();
		try {
			System.out.println(userIdJSON);
				JSONObject json = new JSONObject(userIdJSON);
				int userId = Integer.parseInt(json.getString("userId"));
				events = dbproxy.getEventDetails(userId);
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return events;
	}

	public List<Post> getHomePageForumPosts(String userIdJSON) {
		// TODO Auto-generated method stub
		List<Forum> forums = new ArrayList<Forum>();
		//System.out.println(userIdJSON);
		forums = getHomePageForumMemberships(userIdJSON);
		System.out.println("Forums are "+forums);
		List<Post> posts = new ArrayList<Post>();
		for(Forum forum : forums)
		{
			System.out.println("forum is "+forum.getForumId());
				int forumId = forum.getForumId();
				List <Post> postsInForum = dbproxy.getPostsInForum(forumId);
				for(Post post : postsInForum)
					posts.add((Post)post);
		}
		return posts;
	}
}
