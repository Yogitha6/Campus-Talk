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

import com.campusTalk.Controller.userController;

@Path("/Login")
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
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postMsg(InputStream input)
	{
		StringBuilder strBuilder = new StringBuilder();
		userController uc = new userController();
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
}