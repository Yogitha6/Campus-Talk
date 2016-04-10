package com.campusTalk.database;

import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.campusTalk.model.*;
import com.campusTalk.model.User;

public class DbHelper implements DbProxyInterface {

	//sessionFactory object contains all the data of Hibernate config file 
	public SessionFactory sessionFactory;
	
	public DbHelper(){
		this.initializeSessionFactory();
	}
	
	public void initializeSessionFactory(){
		try{
			if(this.sessionFactory == null){
				this.sessionFactory = new Configuration().configure().buildSessionFactory();
			}
		}catch(Throwable e){
			System.out.println("Failure to create session factory.."+e);
			throw new ExceptionInInitializerError(e);
		}
	}
	

	@Override
	public void saveEventDetails(Event event) {
		// TODO Auto-generated method stub
		
		// Opening a session to create a database connection
		Session session = this.sessionFactory.openSession();
		
		// for CRUD operations, we need to create a transaction
		Transaction tx = null;
		
		// adding a try catch block for maintaining the atomicity of transaction
		try{
			tx = session.beginTransaction();
			System.out.println("DbHelper..saveEventDetails");
			
			//session save inserts the object into DB
			session.save(event);
			
			tx.commit();
			System.out.println("Event saved");
		}
		catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
	}

	@Override
	public void saveForumDetails(Forum forum) {
		// TODO Auto-generated method stub
		System.out.println("DbHelper...saveForumResults");
		System.out.println(this.sessionFactory);
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(forum);
			tx.commit();
			System.out.println("Forum Committed..");
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
	}

	@Override
	public List<Forum> getForumResults(String criteria, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void savePostDetails(Post post) {
		// TODO Auto-generated method stub
		System.out.println("DbHelper..savePostDetails");
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(post);
			tx.commit();
			System.out.println("Post Committed..");
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsInForum(int forumId) {
		// TODO Auto-generated method stub
		System.out.println("DbHelper->getPostsInForum...");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Post> postArr = new ArrayList<Post>();
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Post where forumId = :forumId");
			System.out.println("Search for Forum Id...."+forumId);
			query.setParameter("forumId", forumId);
			System.out.println("Query--"+query);
			postArr = query.list();
			System.out.println("No of rows retrieved.."+postArr.size());
			for(Post post : postArr){
				System.out.println("Post - id----"+post.getPostId());
				System.out.println("Post - Description----"+post.getDescription());
				System.out.println("Post - Forum id----"+post.getForumId());
			}
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return postArr;
	}

	@Override
	public void saveReplyDetails(Reply reply) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(reply);
			tx.commit();
			System.out.println("Reply Committed..");
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
	}

	@Override
	public List<Reply> getRepliesToPost(int postId) {
		// TODO Auto-generated method stub
		System.out.println("DbHelper->getRepliesToPost...");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Reply> replyArr = new ArrayList<Reply>();
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Reply where postId = :postId");
			query.setParameter("postId", postId);
			replyArr = query.list();
			for(Reply reply : replyArr){
				System.out.println("Reply - id----"+reply.getReplyId());
				System.out.println("Reply - Description----"+reply.getDescription());
				System.out.println("Reply - Post id----"+reply.getPostId());
			}
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return replyArr;
	}

	@Override
	public void saveSubscription(Subscription subscription) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(subscription);
			tx.commit();
			System.out.println("Subscription Committed..");
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
	}

	@Override
	public void deleteSubscription(int userId, int forumId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			System.out.println("DbHelper - Delete Subscription..");
			Query query = session.createQuery("delete from Subscription where userId = :userId and forumId = :forumId");
			query.setParameter("userId", userId);
			query.setParameter("forumId", forumId);
			int result = query.executeUpdate();
			System.out.println("Execute query..");
			if(result > 0){
				tx.commit();
				System.out.println("Subscription Removed..");
			}else{
				System.out.println("Subscription does not exist");
				tx.rollback();
			}
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
	}

	public String getUserPassword(String emailId) {
		// TODO Auto-generated method stub
		String password = "";
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from User where emailId = :emailId");
			query.setString("emailId", emailId);
			User user = (User) query.uniqueResult();
			password = user.getPassword();
			//System.out.println("password from DB is "+password);
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		
		return password;
	}
}
