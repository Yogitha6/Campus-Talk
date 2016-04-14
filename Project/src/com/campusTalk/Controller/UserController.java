package com.campusTalk.Controller;

import org.json.*;

import com.campusTalk.database.DbProxy;

public class UserController {
	
	DbProxy db = new DbProxy();

	public int authenticate(String userCredentials) {
		int statusCode = 401;
		System.out.println(userCredentials);
		try {
			JSONObject json = new JSONObject(userCredentials);
			String username = json.getString("name");
			String password = json.getString("pwd");
			//System.out.println("name and password "+username+" "+password);
			String actualPassword = db.getPassword(username);
			//System.out.println("Actual Password is "+actualPassword);
				if(!password.equals(""))
				{
					if(actualPassword.equals(password))
					{
						statusCode = 200;
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
		
		return statusCode;
	}
}
