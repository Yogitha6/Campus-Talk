package com.campusTalk.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.campusTalk.Beans.Event;
import com.mysql.jdbc.PreparedStatement;

public class DBHelper {

	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(CTCommons.DB_DRIVER);
			connection = DriverManager.getConnection(CTCommons.DB_URL + "/"
					+ CTCommons.DB_NAME, CTCommons.DB_USER,
					CTCommons.DB_PSSWD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void addEventDetails(Event e) 
	{
		Connection connection = getConnection();
		if(connection != null)
		{
			System.out.println("Connection established");
			String query = "INSERT INTO event(name,description,startDate,endDate,location,link) VALUES(?,?,?,?,?,?)";
			try{
				PreparedStatement pstatement = (PreparedStatement) connection.prepareStatement(query);
				pstatement.setString(1, e.getEventName());
				pstatement.setString(2, e.getEventDescription());
				
				// bug in setting a string into sql db - datetime format
				pstatement.setString(3, e.getEventStartDate());
				pstatement.setString(4, e.getEventEndDate());
				
				pstatement.setString(5, e.getEventLocation());
				pstatement.setString(6, e.getEventLink());
				
				pstatement.execute();
				pstatement.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		else
		{
			System.out.println("Couldn't create connection object");
		}
	}
}