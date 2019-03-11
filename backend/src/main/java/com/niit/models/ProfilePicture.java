package com.niit.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="profilepicture_model")
public class ProfilePicture 
{
	@Id
	private String email;
	@Lob
	private byte[] profilepic;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getProfilepic() {
		return profilepic;
	}
	public void setProfilepic(byte[] profilepic) {
		this.profilepic = profilepic;
	}
	
	
}
