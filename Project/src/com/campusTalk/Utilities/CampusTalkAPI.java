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
	public Response postMsg(InputStream input)
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
		// System.out.println("Data Received: " + strBuilder.toString());
		// code to validate the credentials by checking DB entries
		int statusCode = uc.authenticate(strBuilder.toString());
		//System.out.println(statusCode);
		return Response.status(statusCode).build();	
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
}