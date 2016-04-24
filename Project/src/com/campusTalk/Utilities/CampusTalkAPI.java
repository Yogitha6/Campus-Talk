package com.campusTalk.Utilities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.campusTalk.Controller.*;
import com.campusTalk.model.*;
import com.google.gson.Gson;

@Path("/CampusTalkAPI")
public class CampusTalkAPI{
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg)
	{
		/*String output = "Welcome to World of Rest: "+msg;
		System.out.println(output);*/
		return Response.status(200).build();
	}
	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();
		UserController uc = new UserController();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int userId = uc.authenticate(strBuilder.toString());
		//System.out.println(statusCode);
		if(userId!=0)
		{
		 return Response.ok(Integer.toString(userId)).build();
		}
		else
			return Response.status(401).build();
	}
	
	@POST
	@Path("unsubscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	public void unsubscribeUser(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();		
		ForumController forumController = new ForumController();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		forumController.unsubscribe(strBuilder.toString());	
	}
	
	@POST
	@Path("subscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	public void subscribeUser(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();		
		ForumController forumController = new ForumController();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		forumController.subscribe(strBuilder.toString());	
	}
	
	@POST
	@Path("createForum")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createForum(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();		
		ForumController forumController = new ForumController();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		forumController.createForum(strBuilder.toString());	
	}
	
	@POST
	@Path("createPost")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createPost(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();		
		PostController postController = new PostController();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		postController.createPost(strBuilder.toString());	
	}
	
	@POST
	@Path("createReply")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createReply(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();		
		ReplyController replyController = new ReplyController();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		replyController.createReply(strBuilder.toString());	
	}
	
	@POST
	@Path("getTopics")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getTopics(InputStream input) throws JSONException
	{	
		List<Topic> topicArr = new ArrayList<Topic>();
		ForumController forumController = new ForumController();
		topicArr = forumController.getTopics();
		//System.out.println("No of topics = "+topicArr.size());
		String topics = new Gson().toJson(topicArr);
        return Response.ok(topics).build();
	}
	
	@POST
	@Path("getPostsAndReplies")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getPostsAndReplies(InputStream input) throws JSONException
	{
		StringBuilder strBuilder = new StringBuilder();	
		ForumController forumController = new ForumController();
		List<PostAndReply> prArr = new ArrayList<PostAndReply>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		prArr = forumController.getPostsAndReplies(strBuilder.toString());
        String pr = new Gson().toJson(prArr);
        return Response.ok(pr).build();
	}
	
	@POST
	@Path("getCountOfSubscribers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getCountOfSubscribers(InputStream input) throws JSONException
	{
		
		StringBuilder strBuilder = new StringBuilder();	
		ForumController forumController = new ForumController();
		int countOfSubscribers = 0;
		JSONObject countOfSubscribed = new JSONObject();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		countOfSubscribers = forumController.getCountOfSubscribers(strBuilder.toString());
		countOfSubscribed.put("countOfSubscribers", countOfSubscribers);
		return Response.ok(countOfSubscribed.toString()).build();
	}
	
	@POST
	@Path("HomePage_Forums")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomePage_ForumPosts(InputStream input) throws JSONException
	{
		StringBuilder strBuilder = new StringBuilder();
		HomePageController hc = new HomePageController();
		List<Post> postsArray = new ArrayList<Post>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		postsArray = hc.getHomePageForumPosts(strBuilder.toString());
        String posts = new Gson().toJson(postsArray);
        return Response.ok(posts).build();
	}
	
	@POST
	@Path("HomePage_Events")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomePage_Events(InputStream input) throws JSONException
	{
		StringBuilder strBuilder = new StringBuilder();
		HomePageController hc = new HomePageController();
		List<Event> eventsArray = new ArrayList<Event>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		eventsArray = hc.getHomePageEvents(strBuilder.toString());
		String events = new Gson().toJson(eventsArray);
        return Response.ok(events).build();
	}
	
	@POST
	@Path("HomePage_ForumMemberships")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomePage_ForumMemberships(InputStream input) throws JSONException
	{
		StringBuilder strBuilder = new StringBuilder();
		HomePageController hc = new HomePageController();
		List<Forum> forumsArray = new ArrayList<Forum>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		forumsArray = hc.getHomePageForumMemberships(strBuilder.toString());
        String forums = new Gson().toJson(forumsArray);
        return Response.ok(forums).build();
	}
	
	@POST
	@Path("createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();		
		UserController userController = new UserController();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				strBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Data Received: " + strBuilder.toString());
		int userId = userController.createUser(strBuilder.toString());
		return Response.ok( Integer.toString(userId) ).build();
	}
	
	@GET
	@Path("getUser/{param}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getUser(@PathParam("param") String userId)
	{
		UserController userController = new UserController();
		User user = userController.getUser(Integer.parseInt(userId));
		user.setPassword("");
		String userObject = new Gson().toJson(user);
		return Response.ok( userObject ).build();
	}
	
	@GET
	@Path("getDomain")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getDomain()
	{
		List<Domain> domainArr = new ArrayList<Domain>();
		ForumController forumController = new ForumController();
		domainArr = forumController.getDomain();
		//System.out.println("No of topics = "+topicArr.size());
		String domains = new Gson().toJson(domainArr);
        return Response.ok(domains).build();
	}
	
	@GET
	@Path("getArea/{param}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getArea(@PathParam("param") String domainId)
	{
		List<Area> areaArr = new ArrayList<Area>();
		ForumController forumController = new ForumController();
		areaArr = forumController.getArea(Integer.parseInt(domainId));
		//System.out.println("No of topics = "+topicArr.size());
		String areas = new Gson().toJson(areaArr);
        return Response.ok(areas).build();
	}
	
	@GET
	@Path("getTopics/{param}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getTopicArea(@PathParam("param") String areaId)
	{
		List<Topic> topicArr = new ArrayList<Topic>();
		ForumController forumController = new ForumController();
		topicArr = forumController.getTopic(Integer.parseInt(areaId));
		//System.out.println("No of topics = "+topicArr.size());
		String topics = new Gson().toJson(topicArr);
        return Response.ok(topics).build();
	}
	
	@GET
	@Path("getUserInterests/{param}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getUserInterests(@PathParam("param") String userId)
	{
		List<Topic> topicArr = new ArrayList<Topic>();
		UserController userController = new UserController();
		topicArr = userController.getUserInterests(Integer.parseInt(userId));
		//System.out.println("No of topics = "+topicArr.size());
		String topics = new Gson().toJson(topicArr);
        return Response.ok(topics).build();
	}
	
	@GET
	@Path("getSearchForums/{param}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getSearchForums(@PathParam("param") String topicId)
	{
		List<Forum> forumArr = new ArrayList<Forum>();
		ForumController forumController = new ForumController();
		forumArr = forumController.getsearchResults(Integer.parseInt(topicId));
		//System.out.println("No of topics = "+topicArr.size());
		String forums = new Gson().toJson(forumArr);
        return Response.ok(forums).build();
	}
	
	@GET
	@Path("getSearchUsers/{param}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getSearchUsers(@PathParam("param") String topicId)
	{
		List<User> userArr = new ArrayList<User>();
		UserController userController = new UserController();
		userArr = userController.getsearchResults(Integer.parseInt(topicId));
		//System.out.println("No of topics = "+topicArr.size());
		String forums = new Gson().toJson(userArr);
        return Response.ok(forums).build();
	}
}