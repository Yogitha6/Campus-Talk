package com.campusTalk.Utilities;

import java.io.InputStream;
import java.io.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.campusTalk.Controller.*;

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
			System.out.println("Error Parsing: - ");
		}
		// System.out.println("Data Received: " + strBuilder.toString());
		// code to validate the credentials by checking DB entries
		int statusCode = uc.authenticate(strBuilder.toString());
		System.out.println(statusCode);
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
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + strBuilder.toString());
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
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + strBuilder.toString());
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
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + strBuilder.toString());
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
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + strBuilder.toString());
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
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + strBuilder.toString());
		replyController.createReply(strBuilder.toString());	
	}
}