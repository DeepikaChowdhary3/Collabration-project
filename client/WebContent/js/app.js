var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider)
{
	$routeProvider
	.when('/addjob',{controller:'JobCtrl',templateUrl:'views/jobform.html'})
	.when('/getalljobs',{controller:'JobCtrl',templateUrl:'views/jobslist.html'})
	.when('/registration',{controller:'UserCtrl',templateUrl:'views/registrationform.html'})
	.when('/login',{controller:'UserCtrl',templateUrl:'views/login.html'})
	.when('/updateuserprofile',{controller:'UserCtrl',templateUrl:'views/updateuserprofile.html'})
	
	.otherwise({templateUrl:'views/home.html'})
})

app.run(function($rootScope,UserService,$location,$cookieStore){//when module gets instantiated, get newly created $rootScope, add logout function to the $rootScope objecgt
	alert('app.run function is getting executed...')
	//Adding a function logout() in $rootScope
	//restore the logged in user details from cookieStore
	//$rootScope.user 
	
	if($rootScope.user==undefined)
	   $rootScope.user=$cookieStore.get('userDetails')
		
	$rootScope.logout=function(){
		alert('Logout function in UserCtrl in $rootScope object')
		UserService.logout().then(
				function(response){
					delete $rootScope.user
					$cookieStore.remove('userDetails')
					$location.path('/login')
				},
				function(response){
					if(response.status==401)
						$location.path('/login')
						
						$location.path('/login')
				})
	}
	//Get the user details from cookie and assign it to the $rootScope variable 'user'
	
})