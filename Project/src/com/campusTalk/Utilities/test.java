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

@Path("/hello")
public class test{
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg)
	{
		String output = "Welcome to World of Rest: "+msg;
		System.out.println(output);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postMsg(InputStream incomingData)
	{
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).build();
		
	}
}

/*import com.mysql.jdbc.PreparedStatement;

	public class test {

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
*/