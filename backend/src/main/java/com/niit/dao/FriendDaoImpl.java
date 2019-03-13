package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.models.Friend;
import com.niit.models.User;

@Repository
@Transactional
public class FriendDaoImpl implements FriendDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<User> getAllSuggestedUsers(String email) 
	{
		Session session=sessionFactory.getCurrentSession();
		String queryString="select * from user_model where email in (select email from user_model where email!=:e1 minus (select toId_email from friend_model where fromId_email=:e2 union select fromId_email from friend_model where toId_email=:e3))";
		SQLQuery query=session.createSQLQuery(queryString);
		query.setString("e1",email);
		query.setString("e2",email);
		query.setString("e3",email);
		query.addEntity(User.class);
		List<User> suggestedUsers=query.list();
		return suggestedUsers;
	}

	public void addFriend(Friend friend) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.save(friend);
		
	}

	public List<Friend> getPendingRequests(String email) 
	{
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where toId.email=:email and status=:status");
		query.setString("email",email);
		query.setCharacter("status",'P');
		List<Friend> pendingRequests=query.list();
		return pendingRequests;
	}

	public void acceptRequest(Friend pendingRequest) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.update(pendingRequest);
	}
	
	public void deleteRequest(Friend pendingRequest) 
	{
		Session session=sessionFactory.getCurrentSession();
		session.delete(pendingRequest);
	}

	public List<User> listOfFriends(String email)
	{
		Session session=sessionFactory.getCurrentSession();
		Query q1=session.createQuery("select f.fromId from Friend f where f.toId.email=:e1 and status='A'");
		q1.setString("e1",email);
		List<User> l1=q1.list();
		Query q2=session.createQuery("select f.toId from Friend f where f.fromId.email=:e2 and status='A'");
		q2.setString("e2",email);
		List<User> l2=q2.list();
		l1.addAll(l2);
		return l1;
	}
}
