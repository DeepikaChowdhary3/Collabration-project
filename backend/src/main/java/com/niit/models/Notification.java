package com.niit.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="notification_model")
public class Notification 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int notificationId;
	private String blogTitle;
	private String rejectionReason;
	private String approvedOrRejected;
	private boolean viewed;
	@ManyToOne
	private User userToBeNotified;
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public String getApprovedOrRejected() {
		return approvedOrRejected;
	}
	public void setApprovedOrRejected(String approvedOrRejected) {
		this.approvedOrRejected = approvedOrRejected;
	}
	public boolean isViewed() {
		return viewed;
	}
	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}
	public User getUserToBeNotified() {
		return userToBeNotified;
	}
	public void setUserToBeNotified(User userToBeNotified) {
		this.userToBeNotified = userToBeNotified;
	}
	
	
	
}
