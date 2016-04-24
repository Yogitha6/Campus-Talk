package com.campusTalk.Driver;

import java.util.*;
import com.campusTalk.database.*;
import com.campusTalk.model.*;

public class Driver {
	
	public static DbProxy dbproxy = new DbProxy();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Forum forum = new Forum("Pallavi",100,null,75);
		//dbproxy.saveForumDetails(forum);
		Post post = new Post(1,"Pallavi Test",102,new Date(),5);
		//dbproxy.savePostDetails(post);
		Reply reply = new Reply(1, "First Reply", 100, new Date(), 1);
		//dbproxy.saveReplyDetails(reply);
		Subscription subscription = new Subscription(100,11);
		//dbproxy.saveSubscription(subscription);
		//dbproxy.deleteSubscription(100,5);
		List<Post> posts= new ArrayList<Post>();
		//posts = dbproxy.getPostsInForum(5);
		List<Reply> replies= new ArrayList<Reply>();
		//replies = dbproxy.getRepliesToPost(1);
		//System.out.println("Count of subscribers - "+dbproxy.getCountOfSubscribers(3,0));
		//System.out.println("Count of subscribers - "+dbproxy.getCountOfSubscribers(3,102));
		List<PostAndReply> prArr= new ArrayList<PostAndReply>();
		//prArr = dbproxy.getPostsAndReplies(1);
		//System.out.println("prArr length - "+prArr.size());
		//User user = dbproxy.getUser(101);
		List<Topic> topics = new ArrayList<Topic>();
		//topics = dbproxy.getTopics();
	}
}
