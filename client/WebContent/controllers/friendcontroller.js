app.controller('FriendCtrl',function($scope,$location,FriendService)
{

	function getAllSuggestedUsers()
	{
		FriendService.getAllSuggestedUsers().then
		(
		function(response)
		{
			$scope.suggestedUsers=response.data
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	function getPendingRequests()
	{
		FriendService.getPendingRequests().then
		(
		function(response)
		{
			$scope.pendingRequests=response.data
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	getAllSuggestedUsers()
	getPendingRequests()
	
	$scope.addFriend=function(toId)
	{
		FriendService.addFriend(toId).then
		(
		function(response)
		{
			getAllSuggestedUsers()
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.acceptRequest=function(pendingRequest)
	{
		FriendService.acceptRequest(pendingRequest).then
		(
		function(response)
		{
			getPendingRequests()
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.deleteRequest=function(pendingRequest)
	{
		FriendService.deleteRequest(pendingRequest).then
		(
		function(response)
		{
			getPendingRequests()
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	function getAllFriends()
	{
		FriendService.getAllFriends().then
		(
		function(response)
		{
			$scope.friends=response.data
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	getAllFriends()
})