package database;

import java.util.List;

import model.Forum;
import model.Post;
import model.Reply;
import model.Subscription;

public class DbProxy implements DbProxyInterface {
	
	private DbHelper dbhelper = null;
	public DbProxy(){
		System.out.println("11");
		this.getInstance();
	};

	public void getInstance(){
		if(this.dbhelper == null){
			System.out.println("22");
			this.dbhelper = new DbHelper();
		}
		System.out.println("33");
	}
	
	@Override
	public void saveForumDetails(Forum forum) {
		// TODO Auto-generated method stub
		System.out.println(this.dbhelper);
		this.dbhelper.saveForumDetails(forum);
	}

	@Override
	public List<Forum> getForumResults(String criteria, int userId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getForumResults(criteria, userId);
	}

	@Override
	public void savePostDetails(Post post) {
		// TODO Auto-generated method stub
		System.out.println(this.dbhelper);
		this.dbhelper.savePostDetails(post);
	}

	@Override
	public List<Post> getPostsInForum(int forumId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getPostsInForum(forumId);
	}

	@Override
	public void saveReplyDetails(Reply reply) {
		// TODO Auto-generated method stub
		this.dbhelper.saveReplyDetails(reply);
	}

	@Override
	public List<Reply> getRepliesToPost(int postId) {
		// TODO Auto-generated method stub
		return this.dbhelper.getRepliesToPost(postId);
	}

	@Override
	public void saveSubscription(Subscription subscription) {
		// TODO Auto-generated method stub
		this.dbhelper.saveSubscription(subscription);
	}

	@Override
	public void deleteSubscription(int userId, int forumId) {
		// TODO Auto-generated method stub
		this.dbhelper.deleteSubscription(userId, forumId);
	}

}
