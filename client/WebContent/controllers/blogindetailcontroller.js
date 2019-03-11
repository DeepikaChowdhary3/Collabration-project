app.controller('BlogInDetailCtrl',function($scope,BlogPostService,$routeParams,$location,$sce)
{
	var blogPostId=$routeParams.blogpostid
	alert('blogpost id is '+blogPostId)
	$scope.isRejected=false
	
	BlogPostService.getBlog(blogPostId).then
	(
	function(response)
	{
		$scope.blogPost=response.data
		$scope.content=$sce.trustAsHtml($scope.blogPost.blogContent)
	},
	function(response)
	{
		if(response.status==401)
			$location.path('/login')
	})
	
	$scope.approveBlogPost=function(blogPost)
	{
		BlogPostService.approveBlogPost(blogPost).then
		(
		function(response)
		{
			$location.path('/blogswaitingforapproval')
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.rejectBlogPost=function(blogPost,rejectionReason)
	{
		BlogPostService.rejectBlogPost(blogPost,rejectionReason).then
		(
		function(response)
		{
			$location.path('/blogwaitingforapproval')
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
		
	}
	
	BlogPostService.hasUserLikedBlogPost(blogPostId).then
	(
	function(response)
	{
		if(response.data=='')
			$scope.isLiked=false
			else
				$scope.isLiked=true
	},
	function(response)
	{
		if(response.status==401)
			$location.path('/login')
	})
	
	$scope.updateLikes=function(blogPostId)
	{
		BlogPostService.updateLikes(blogPostId).then
		(
		function(response)
		{
			$scope.isLiked=!$scope.isLiked
			$scope.blogPost=response.data
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.addBlogComment=function(commentTxt,blogPost)
	{
		$scope.blogComment={}
		$scope.blogComment.commentTxt=commentTxt
		$scope.blogComment.blogPost=blogPost
		console.log($scope.blogComment)
		BlogPostService.addBlogComment($scope.blogComment).then
		(
		function(response)
		{
			$scope.commentTxt=''
			$scope.blogComment=response.data
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.getAllBlogComments=function(blogPostId)
	{
		BlogPostService.getAllBlogComments(blogPostId).then
		(
		function(response)
		{
			$scope.blogComments=response.data
		},
		function(response)
		{
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.rejected=function()
	{
		$scope.isRejected=true
			
	}
})