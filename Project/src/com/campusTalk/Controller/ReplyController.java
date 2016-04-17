package com.campusTalk.Controller;

import java.util.Date;
import org.json.*;
import com.campusTalk.database.*;
import com.campusTalk.model.*;

public class ReplyController {
	
	private DbProxy dbproxy = null;
	
	public ReplyController(){
		dbproxy = new DbProxy();
	}

	public void createReply(String replyDetails) {
		try {
				JSONObject json = new JSONObject(replyDetails);
				int userId = Integer.parseInt(json.getString("userId"));
				int postId = Integer.parseInt(json.getString("postId"));
				String replyDescription = json.getString("replyDescription");
				Date dateCreated = new Date();
				//System.out.println("Reply Controller createReply - userId, postId, replyDescription, dateCreated "+userId+" "+postId+" "+replyDescription+" "+dateCreated);
				Reply reply = new Reply(1,replyDescription,userId,dateCreated,postId);
				dbproxy.saveReplyDetails(reply);
			} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
}