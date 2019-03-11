package com.niit.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogPostDao;
import com.niit.dao.NotificationDao;
import com.niit.dao.UserDao;
import com.niit.models.BlogPost;
import com.niit.models.ErrorClazz;
import com.niit.models.Notification;
import com.niit.models.User;

@RestController
public class BlogPostController {

	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private NotificationDao notificationDao;
	
	@RequestMapping(value="/addblog",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(HttpSession session,@RequestBody BlogPost blogPost)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPost.setPostedOn(new Date());
		User author=userDao.getUser(email);
		blogPost.setAuthor(author);
		try
		{
			if(author.getRole().equals("ADMIN"))
				blogPost.setApproved(true);
			
			blogPostDao.addBlogPost(blogPost);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(9,"Unable to insert blogpost "+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/blogsapproved",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogsApproved(HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogsApproved=blogPostDao.getBlogsApproved();
		return new ResponseEntity<List<BlogPost>>(blogsApproved,HttpStatus.OK);
	}
	
	@RequestMapping(value="/blogswaitingforapproval",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogsWaitingForApproval(HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN"))
		{
			ErrorClazz errorClazz=new ErrorClazz(10,"Access Denied! You are not authorized to view the blogs waiting for approval");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogsWaitingForApproval=blogPostDao.getBlogsWaitingForApproval();
		return new ResponseEntity<List<BlogPost>>(blogsWaitingForApproval,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblog/{blogPostId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlog(HttpSession session,@PathVariable int blogPostId)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		BlogPost blogPost=blogPostDao.getBlog(blogPostId);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/approveblogpost",method=RequestMethod.PUT)
	public ResponseEntity<?> approveBlogPost(HttpSession session,@RequestBody BlogPost blogPost)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN"))
		{
			ErrorClazz errorClazz=new ErrorClazz(10,"Access Denied! You are not authorized to view the blogs waiting for approval");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPost.setApproved(true);
		blogPostDao.approveBlogPost(blogPost);
		
		Notification notification=new Notification();
		notification.setApprovedOrRejected("Approved");
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setUserToBeNotified(blogPost.getAuthor());
		notificationDao.addNotification(notification);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectblogpost/{rejectionReason}",method=RequestMethod.PUT)
	public ResponseEntity<?> rejectBlogPost(HttpSession session,@RequestBody BlogPost blogPost,@PathVariable String rejectionReason)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN"))
		{
			ErrorClazz errorClazz=new ErrorClazz(10,"Access Denied! You are not authorized to view the blogs waiting for approval");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		blogPostDao.rejectBlogPost(blogPost);
		
		Notification notification=new Notification();
		notification.setApprovedOrRejected("Rejected");
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setUserToBeNotified(blogPost.getAuthor());
		notification.setRejectionReason(rejectionReason);
		notificationDao.addNotification(notification);
		
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
