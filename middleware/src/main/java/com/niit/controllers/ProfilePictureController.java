package com.niit.controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.niit.dao.ProfilePictureDao;
import com.niit.models.ErrorClazz;
import com.niit.models.ProfilePicture;



@RestController
public class ProfilePictureController 
{
	@Autowired
	private ProfilePictureDao profilePictureDao;
	
	@RequestMapping(value="/uploadprofilepic",method=RequestMethod.POST)
	public ResponseEntity<?> uploadProfilePicture(HttpSession session,@RequestParam MultipartFile profilepic)
	{
		String email=(String)session.getAttribute("loginId");
		if(email==null)
		{
			ErrorClazz errorClazz=new ErrorClazz(7,"Please login..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		ProfilePicture profilePicture=new ProfilePicture();
		profilePicture.setEmail(email);
		try
		{
			profilePicture.setProfilepic(profilepic.getBytes());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		profilePictureDao.saveOrUpdateProfilePicture(profilePicture);
		return new ResponseEntity<ProfilePicture>(profilePicture,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getimage",method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilepic(@RequestParam String email,HttpSession session)
	{
		String authEmail=(String)session.getAttribute("loginId");
		if(authEmail==null)
		{
			return null;
		}
		ProfilePicture profilePicture=profilePictureDao.getProfilePicture(email);
		if(profilePicture==null)
			return null;
		else
			return profilePicture.getProfilepic();
	}
}












