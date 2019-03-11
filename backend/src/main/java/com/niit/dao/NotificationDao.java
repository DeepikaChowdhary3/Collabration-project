package com.niit.dao;

import java.util.List;

import com.niit.models.Notification;

public interface NotificationDao 
{
	void addNotification(Notification notification);
	List<Notification> getAllNotificationsNotViewed(String email);
	Notification getNotification(int notificationId);
	void updateNotificationViewedStatus(int notificationId);
}
