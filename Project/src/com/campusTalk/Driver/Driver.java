package com.campusTalk.Driver;

import java.util.*;
import com.campusTalk.database.*;
import com.campusTalk.model.*;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbProxy dbproxy = new DbProxy();
		Forum forum = new Forum(1,"Pallavi",100,null,75);
		//dbproxy.saveForumDetails(forum);
		Post post = new Post(1,"Strategy Pattern",100,new Date(),1);
		//dbproxy.savePostDetails(post);
		Reply reply = new Reply(1, "First Reply", 100, new Date(), 1);
		//dbproxy.saveReplyDetails(reply);
		Subscription subscription = new Subscription(100,11);
		//dbproxy.saveSubscription(subscription);
		List<Post> posts= new ArrayList<Post>();
		posts = dbproxy.getPostsInForum(5);
		List<Reply> replies= new ArrayList<Reply>();
		replies = dbproxy.getRepliesToPost(1);
		dbproxy.deleteSubscription(100,5);
	}

}
