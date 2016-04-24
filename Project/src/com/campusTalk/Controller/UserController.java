package com.campusTalk.Controller;

import java.util.Date;
import java.util.List;

import org.json.*;

import com.campusTalk.database.DbProxy;
import com.campusTalk.model.Topic;
import com.campusTalk.model.User;
import com.campusTalk.model.UserTopic;

public class UserController {
	
	private DbProxy dbproxy = null;
	
	public UserController(){
		dbproxy = new DbProxy();
	}

	public int authenticate(String userCredentials) {
		int statusCode = 401;
		System.out.println(userCredentials);
		int userId=0;
		try {
			JSONObject json = new JSONObject(userCredentials);
			String username = json.getString("name");
			String password = json.getString("pwd");
			//System.out.println("name and password "+username+" "+password);
			User user = dbproxy.getUser(username);
			String actualPassword = user.getPassword();
			System.out.println("Actual Password is "+actualPassword);
				if(!password.equals(""))
				{
					if(actualPassword.equals(password))
					{
						statusCode = 200;
						userId = user.getUserId();
					}
					else
					{
						statusCode = 401;
					}
				}
				else
					statusCode = 401;
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	public int createUser(String userDetails) {
		int userId = 0;
		try {
				JSONObject json = new JSONObject(userDetails);
				String firstName = json.getString("firstName");
				String lastName = json.getString("lastName");
				String emailId = json.getString("emailId");
				String password = json.getString("password");
				String major = json.getString("major");
				String topics = json.getString("topics");
				topics = topics.substring(1, topics.length() - 1);
				//System.out.println("Forum Controller createForum - userId, topicId, forumDescription, dateCreated "+userId+" "+topicId+" "+forumDescription+" "+dateCreated);
				User user = new User(firstName, lastName, emailId, password, major);
				dbproxy.saveUserDetails(user);
				if (topics.length() > 2){
					for (String topic: topics.split(",")){
						topic = topic.substring(1, topic.length() - 1);
				        UserTopic userTopic = new UserTopic(user.getUserId(), Integer.parseInt(topic));
				        dbproxy.saveUserTopic(userTopic);
				    }
				}
				userId = user.getUserId();
				
			} catch (JSONException e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	public User getUser( int userId ){
		return dbproxy.getUser( userId );
	}

	public List<Topic> getUserInterests(int userId) {
		return dbproxy.getUserInterests(userId);
	}
}
