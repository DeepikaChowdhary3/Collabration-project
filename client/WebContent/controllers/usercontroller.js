app.controller('UserCtrl',function($scope,UserService,$location,$rootScope,$cookieStore)
{

	$scope.userRegistration=function(user)
	{
		UserService.userRegistration(user).then
		(
		function(response)
		{
			$location.path('/login')
		},
		function(response)
		{
			$scope.error=response.data
		})
	}
	
	$scope.login=function(user)
	{
		UserService.login(user).then
		(
		function(response)
		{
			$rootScope.user=response.data
			$cookieStore.put('userDetails',response.data)
			$location.path('/home')
		},
		function(response)
		{
			$scope.error=response.data
		})
	}
	
	if($rootScope.user!=undefined)
	{
		UserService.getUser().then
		(
		function(response)
		{
			$scope.user=response.data
		},
		function(response)
		{
			console.log(response.status)
			console.log(response.data)
		})
	}
	
	$scope.updateUserProfile=function(user)
	{
		UserService.updateUserProfile(user).then
		(
		function(response)
		{
			alert('Updated user details successfully')
			$rootScope.user=user
			$cookieStore.put('userDetails',user)
			$location.path('/home')
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
			
		    $scope.error=response.data
		})
	}
	
})

