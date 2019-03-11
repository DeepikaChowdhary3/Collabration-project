app.factory('NotificationService',function($http)
{

	var notificationService={}
	var BASE_URL="http://localhost:8085/middleware"
		
	notificationService.getNotificationsNotViewed=function()
	{
		return $http.get(BASE_URL+"/getnotificationsnotviewed")
	}
	
	notificationService.getNotification=function(notificationId)
	{
		return $http.get(BASE_URL+"/getnotification/"+notificationId)
	}
	
	notificationService.updateNotification=function(notificationId)
	{
		return $http.put(BASE_URL+"/updatenotification/"+notificationId)
	}
	
	return notificationService;
})
