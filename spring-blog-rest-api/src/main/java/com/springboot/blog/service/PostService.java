package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto); // To store the post object
	
	PostResponse getAllPosts(int pageNo,int pageSize,String soryBy,String sortDir);// Get posts by sorting
	
	PostDto getPostById(long id); // Get post object by id
	
	PostDto updatePost(PostDto postDto,long id); // Update the post by id

	void deletPostById(long id); // To delete the post

	void createPosts(PostDto[] postDto); // To store the multiple post objects in an array

	

}
