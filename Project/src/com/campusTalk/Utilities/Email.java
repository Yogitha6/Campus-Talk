package com.campusTalk.Utilities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class Email {

	public int sendEmail(String emailId) {
		
		try {
			JSONObject json = new JSONObject(emailId);
			String emailTo = json.getString("name");

		      // Sender's email ID needs to be mentioned
		      String emailFrom = "campus.talk.cu@gmail.com";
			  String password = "csci5448";

				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailFrom, password);
					}
				  });

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(emailFrom));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(emailTo));
					message.setSubject("Campus Talk Invitation");
					message.setText("Hellooo !!!"
						+ "\n\n There is a new online collobration website on campus. Please check it out at www.campustalk.com!");

					Transport.send(message);

					System.out.println("Done");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}		      
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return 200;
	}
}