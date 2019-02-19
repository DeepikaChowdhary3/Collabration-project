package com.niit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserDao;
import com.niit.models.ErrorClazz;
import com.niit.models.User;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public ResponseEntity<?> userRegistration(@RequestBody User user)
	{
		if(!userDao.isEmailUnique(user.getEmail())) 
		{
			ErrorClazz errorClazz=new ErrorClazz(3,"Email already exists... please choose a different email id");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(!userDao.isPhonenumberUnique(user.getPhonenumber()))
		{
			ErrorClazz errorClazz=new ErrorClazz(4,"Phone number already exists... please enter a different phone number");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(user.getRole()==""||user.getRole()==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Role cannot be null");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try 
		{
			userDao.userRegistration(user);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(2,"Unable to register user details.."+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session)
	{
		User validUser=userDao.login(user);
		if(validUser==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(6,"Email/Password is incorrect..Please enter valid credentials");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);	
		}
		else
		{
			System.out.println("Session Id is "+session.getId());
			System.out.println("Session created time "+session.getCreationTime());
			validUser.setOnline(true);
			userDao.updateUser(validUser);
			session.setAttribute("loginId",user.getEmail());
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		User user=userDao.getUser(email);
		user.setOnline(false);
		userDao.updateUser(user);
		session.removeAttribute("loginId");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuser",method=RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		
		User user=userDao.getUser(email);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateuser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user,HttpSession session)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		if(!userDao.isPhonenumberUnique(user.getPhonenumber()))
		{
			ErrorClazz errorClazz=new ErrorClazz(4,"Phone number already exists... please enter a different phone number");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(user.getRole()=="" || user.getRole()==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(5,"Role cannot be null");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		try 
		{
			userDao.updateUser(user);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(8,"Unable to update user profile "+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
