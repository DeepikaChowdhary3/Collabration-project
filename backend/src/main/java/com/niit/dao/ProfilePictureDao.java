package com.niit.dao;

import com.niit.models.ProfilePicture;

public interface ProfilePictureDao 
{
	void saveOrUpdateProfilePicture(ProfilePicture profilePicture);
	
	ProfilePicture getProfilePicture(String email);
}
