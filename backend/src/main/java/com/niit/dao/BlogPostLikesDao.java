package com.niit.dao;

import com.niit.models.BlogPost;
import com.niit.models.BlogPostLikes;

public interface BlogPostLikesDao 
{
	BlogPostLikes hasUserLikedBlogPost(int blogPostId,String email);
	BlogPost updateLikes(int blogPostId,String email);
}
