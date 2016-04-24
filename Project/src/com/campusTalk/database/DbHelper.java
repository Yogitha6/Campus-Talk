package com.campusTalk.database;

import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.campusTalk.model.*;

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
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(forum);
			tx.commit();
			//System.out.println("Forum Committed..");
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
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(post);
			tx.commit();
			//System.out.println("Post Committed..");
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
		//System.out.println("DbHelper->getPostsInForum...");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Post> postArr = new ArrayList<Post>();
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Post where forumId = :forumId order by createdDate desc");
			query.setParameter("forumId", forumId);
			postArr = query.list();
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
			//System.out.println("Reply Committed..");
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
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Reply> replyArr = new ArrayList<Reply>();
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Reply where postId = :postId order by createdDate desc");
			query.setParameter("postId", postId);
			replyArr = query.list();
/*			for(Reply reply : replyArr){
				System.out.println("Reply - id----"+reply.getReplyId());
				System.out.println("Reply - Description----"+reply.getDescription());
				System.out.println("Reply - Post id----"+reply.getPostId());
			}*/
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
			session.saveOrUpdate(subscription);
			tx.commit();
			//System.out.println("Subscription Committed..");
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
			Query query = session.createQuery("delete from Subscription where userId = :userId and forumId = :forumId");
			query.setParameter("userId", userId);
			query.setParameter("forumId", forumId);
			int result = query.executeUpdate();
			System.out.println("Execute query..");
			if(result > 0){
				tx.commit();
				//System.out.println("Subscription Removed..");
			}else{
				//System.out.println("Subscription does not exist");
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
	
	public int getCountOfSubscribers(int forumId, int userId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		int countOfSubscribers = 0;
		try{
			tx = session.beginTransaction();
			Query query = null;
			if(userId == 0){
				query = session.createQuery("select count(*) from Subscription where forumId = :forumId");
				query.setParameter("forumId", forumId);
			} else{
				query = session.createQuery("select count(*) from Subscription where forumId = :forumId and userId = :userId");
				query.setParameter("forumId", forumId);
				query.setParameter("userId", userId);
			}

			Long count = (Long)query.uniqueResult();
			countOfSubscribers = count.intValue();
			//System.out.println("No of subscribers.."+countOfSubscribers);
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
		return countOfSubscribers;
	}
	
	@SuppressWarnings("unchecked")
	public List<PostAndReply> getPostsAndReplies(int forumId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Post> postArr = new ArrayList<Post>();
		List<PostAndReply> prArray = new ArrayList<PostAndReply>();
		
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Post where forumId = :forumId order by createdDate desc");
			query.setParameter("forumId", forumId);
			postArr = query.list();
			System.out.println("No of posts.."+postArr.size());
			for(Post post : postArr){
				int postId = post.getPostId();
				System.out.println("Post id----"+postId);
				List<Reply> replies = getRepliesToPost(postId);
				User user = getUser(post.getCreatedBy());
				String postCreatorName = user.getFirstname()+' '+user.getLastname();
				PostAndReply pr = new PostAndReply();
				pr.setPost(post);
				pr.setReplies(replies);
				pr.setPostCreatorName(postCreatorName);
				prArray.add(pr);
				//System.out.println("Post - Description----"+post.getDescription());
				//System.out.println("No of Replies----"+replies.size());
			}
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return prArray;
	}

	@Override
	public User getUser(int userId) {
		// TODO Auto-generated method stub
		System.out.println("DbHelper->getUser...");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		User user = new User();
		try{
			tx = session.beginTransaction();
			user = (User)session.get(User.class, userId); 
			//System.out.println("First Name - "+user.getFirstname());
			//System.out.println("Last Name - "+user.getLastname());
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return user;
	}
	
	public User getUser(String username) {
		// TODO Auto-generated method stub
		System.out.println("DbHelper->getUser...");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		User user = new User();
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from User where emailId = :emailId");
			query.setString("emailId", username);
			user = (User) query.uniqueResult();
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return user;
	}

	@Override
	public List<Topic> getTopics() {
		// TODO Auto-generated method stub
		List<Topic> topicArr = new ArrayList<Topic>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Topic");
			topicArr = query.list();
			/*for(Topic topic : topicArr){
				System.out.println("Topic Description----"+topic.getTopicDescription());
				System.out.println("Topic Id----"+topic.getTopicId());
			}*/
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return topicArr;
	}

	@Override
	public void saveUserDetails(User user) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
			//System.out.println("Forum Committed..")
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
	public List<Event> getEventDetails(int userId) {
		// TODO Auto-generated method stub
		List<Event> events = new ArrayList<Event>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Event");
			events = query.list();
		}
		catch(HibernateException e){
			if(tx != null){
			tx.rollback();
			e.printStackTrace();
			}
		}
		finally {
		session.close();
			}
		return events;
	}
	@Override
	public List<Forum> getForumsOfaUser(int userId) {
		// TODO Auto-generated method stub
		List<Forum> forums = new ArrayList<Forum>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Subscription where userId = :userId");
			query.setString("userId", String.valueOf(userId));
			List<Subscription> userSubscribedGroups = query.list();
			for(Subscription subsc : userSubscribedGroups)
			{
				query = session.createQuery("from Forum where forumId = :forumId");
				query.setParameter("forumId", subsc.getForumId());
				// add this to a list variable query.uniqueResult();
				forums.add((Forum) query.uniqueResult());
			}
			
		}
		catch(HibernateException e){
			if(tx != null){
			tx.rollback();
			e.printStackTrace();
			}
		}
		finally {
		session.close();
			}
		return forums;
	}

	@Override
	public void saveUserTopic(UserTopic userTopic) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(userTopic);
			tx.commit();
			//System.out.println("Forum Committed..")
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}
		
	}

	public List<Domain> getDomains() {
		List<Domain> domainArr = new ArrayList<Domain>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Domain");
			domainArr = query.list();
			/*for(Topic topic : topicArr){
				System.out.println("Topic Description----"+topic.getTopicDescription());
				System.out.println("Topic Id----"+topic.getTopicId());
			}*/
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return domainArr;
	}

	public List<Area> getAreas(int domainId) {
		List<Area> areaArr = new ArrayList<Area>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Area where domainId = :domainId");
			query.setString("domainId", String.valueOf(domainId));
			areaArr = query.list();
			/*for(Topic topic : topicArr){
				System.out.println("Topic Description----"+topic.getTopicDescription());
				System.out.println("Topic Id----"+topic.getTopicId());
			}*/
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return areaArr;
	}

	public List<Topic> getTopics(int areaId) {
		List<Topic> topicArr = new ArrayList<Topic>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Topic where areaId = :areaId");
			query.setString("areaId", String.valueOf(areaId));
			topicArr = query.list();
			/*for(Topic topic : topicArr){
				System.out.println("Topic Description----"+topic.getTopicDescription());
				System.out.println("Topic Id----"+topic.getTopicId());
			}*/
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return topicArr;
	}

	public List<Topic> getUserInterests(int userId) {
		List<Topic> topicArr = new ArrayList<Topic>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query1 = session.createQuery("select topicId from UserTopic where userId = :userId");
			query1.setString("userId", String.valueOf(userId));
			Query query2 = session.createQuery("from Topic where topicId in (:topicList)");
			query2.setParameterList("topicList", query1.list());
			topicArr = query2.list();
			/*for(Topic topic : topicArr){
				System.out.println("Topic Description----"+topic.getTopicDescription());
				System.out.println("Topic Id----"+topic.getTopicId());
			}*/
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return topicArr;
	}

	public List<Forum> getSearchResults(int topicId) {
		List<Forum> forumArr = new ArrayList<Forum>();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("from Forum where topicId = :topicId");
			query.setString("topicId", String.valueOf(topicId));
			forumArr = query.list();
			/*for(Topic topic : topicArr){
				System.out.println("Topic Description----"+topic.getTopicDescription());
				System.out.println("Topic Id----"+topic.getTopicId());
			}*/
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			session.close();
		}	
		return forumArr;
	}	
}
