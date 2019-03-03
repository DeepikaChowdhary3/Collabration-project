app.factory('BlogPostService',function($http)
{
	var blogPostService={}
	var BASE_URL="http://localhost:8085/middleware"
		
	blogPostService.addBlogPost=function(blogPost)
	{
		return $http.post(BASE_URL+"/addblog",blogPost)
	}
	
	blogPostService.getBlogsApproved=function()
	{
		return $http.get(BASE_URL+"/blogsapproved")
	}
	
	blogPostService.getBlogsWaitingForApproval=function()
	{
		return $http.get(BASE_URL+"/blogswaitingforapproval")
	}
	
	blogPostService.getBlog=function(blogPostId)
	{
		return $http.get(BASE_URL+"/getblog/"+blogPostId)
	}
	
	blogPostService.approveBlogPost=function(blogPost)
	{
		return $http.put(BASE_URL+"/approveblogpost",blogPost)
	}
	
	blogPostService.rejectBlogPost=function(blogPost)
	{
		return $http.put(BASE_URL+"/rejectblogpost",blogPost)
	}
	
	blogPostService.hasUserLikedBlogPost=function(blogPostId)
	{
		return $http.get(BASE_URL+"/hasuserlikedblogpost/"+blogPostId)
	}
	
	blogPostService.updateLikes=function(blogPostId)
	{
		return $http.put(BASE_URL+"/updatelikes/"+blogPostId)
	}
	
	blogPostService.addBlogComment=function(blogComment)
	{
		return $http.post(BASE_URL+"/addblogcomment",blogComment)
	}
	
	blogPostService.getAllBlogComments=function(blogPostId)
	{
		return $http.get(BASE_URL+"/getblogcomments/"+blogPostId)
	}
	
	return blogPostService;
})
		