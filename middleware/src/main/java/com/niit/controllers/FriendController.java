package com.niit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.FriendDao;
import com.niit.dao.UserDao;
import com.niit.models.ErrorClazz;
import com.niit.models.Friend;
import com.niit.models.User;

@RestController
public class FriendController 
{
	@Autowired
	private FriendDao friendDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/suggestedusers",method=RequestMethod.GET)
	public ResponseEntity<?> getAllSuggestedUsers(HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<User> suggestedUsers=friendDao.getAllSuggestedUsers(email);
		return new ResponseEntity<List<User>>(suggestedUsers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/addfriend",method=RequestMethod.POST)
	public ResponseEntity<?> addFriend(HttpSession session,@RequestBody User toId)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User fromId=userDao.getUser(email);
		Friend friend=new Friend();
		friend.setFromId(fromId);
		friend.setToId(toId);
		friend.setStatus('P');
		friendDao.addFriend(friend);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/pendingrequests",method=RequestMethod.GET)
	public ResponseEntity<?> pendingRequests(HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<Friend> pendingRequests=friendDao.getPendingRequests(email);
		return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptrequest",method=RequestMethod.PUT)
	public ResponseEntity<?> acceptRequest(HttpSession session,@RequestBody Friend pendingRequest)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		pendingRequest.setStatus('A');
		friendDao.acceptRequest(pendingRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleterequest",method=RequestMethod.PUT)
	public ResponseEntity<?> deleteRequest(HttpSession session,@RequestBody Friend pendingRequest)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		friendDao.deleteRequest(pendingRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/listoffriends",method=RequestMethod.GET)
	public ResponseEntity<?> listOfFriends(HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<User> friends=friendDao.listOfFriends(email);
		return new ResponseEntity<List<User>>(friends,HttpStatus.OK);
	}
}
