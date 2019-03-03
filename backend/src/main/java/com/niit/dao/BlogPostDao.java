package com.niit.dao;

import java.util.List;

import com.niit.models.BlogPost;

public interface BlogPostDao 
{
	void addBlogPost(BlogPost blogPost);
	List<BlogPost> getBlogsApproved();
	List<BlogPost> getBlogsWaitingForApproval();
	BlogPost getBlog(int blogPostId);
	void approveBlogPost(BlogPost blogPost);
	void rejectBlogPost(BlogPost blogPost);
	
}
