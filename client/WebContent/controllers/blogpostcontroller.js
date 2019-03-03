app.controller('BlogPostCtrl',function($scope,BlogPostService,$location,$rootScope)
{
	
	$scope.addBlogPost=function(blogPost)
	{ 
		BlogPostService.addBlogPost(blogPost).then
		(
		function(response)
		{
			if($rootScope.user.role!='ADMIN')
			alert('BlogPost added successfully and it is waiting for approval')
			$location.path('/home')
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
			$scope.error=response.data
		})
	}
	
	if($rootScope.user!=undefined)
		BlogPostService.getBlogsApproved().then
		(
		function(response)
		{
			$scope.blogsApproved=response.data 
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
		
	if($rootScope.user.role=='ADMIN')
		BlogPostService.getBlogsWaitingForApproval().then
		(
		function(response)
		{
			$scope.blogsWaitingForApproval=response.data
		},
		function(response)
		{
			if(response.status==401 && response.data.errorCode==7)
				$location.path('/login')
			$scope.error=response.data
		})
		
	$scope.addBoldTags=function()
	{
		$scope.blogPost.blogContent=$scope.blogPost.blogContent+"<b></b>"
	}
})