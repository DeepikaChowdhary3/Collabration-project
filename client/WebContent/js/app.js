var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider)
{
	$routeProvider
	.when('/addjob',{controller:'JobCtrl',templateUrl:'views/jobform.html'})
	.when('/getalljobs',{controller:'JobCtrl',templateUrl:'views/jobslist.html'})
	
	.when('/registration',{controller:'UserCtrl',templateUrl:'views/registrationform.html'})
	.when('/login',{controller:'UserCtrl',templateUrl:'views/login.html'})
	.when('/updateuserprofile',{controller:'UserCtrl',templateUrl:'views/updateuserprofile.html'})
	
	.when('/addblog',{controller:'BlogPostCtrl',templateUrl:'views/blogpostform.html'})
	.when('/blogsapproved',{controller:'BlogPostCtrl',templateUrl:'views/blogsapproved.html'})
	.when('/blogswaitingforapproval',{controller:'BlogPostCtrl',templateUrl:'views/blogswaitingforapproval.html'})
	
	.when('/getblogwaitingforapproval/:blogpostid',{controller:'BlogInDetailCtrl',templateUrl:'views/blogapprovalform.html'})
	.when('/getblogapproved/:blogpostid',{controller:'BlogInDetailCtrl',templateUrl:'views/blogindetail.html'})
	
	.otherwise({templateUrl:'views/home.html'})
})

app.run(function($rootScope,UserService,$location,$cookieStore){//when module gets instantiated, get newly created $rootScope, add logout function to the $rootScope objecgt
	/*alert('app.run function is getting executed...')*/
	
	
	if($rootScope.user==undefined)
	   $rootScope.user=$cookieStore.get('userDetails')
		
	$rootScope.logout=function(){
		/*alert('Logout function in UserCtrl in $rootScope object')*/
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
	
})