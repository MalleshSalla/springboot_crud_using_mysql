package com.springboot.blog.controller;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	// create multiple posts at a time
	@PostMapping("/all")
	public ResponseEntity<String> createPosts(@Valid @RequestBody PostDto[] postDto) {
		
		postService.createPosts(postDto);
		return new ResponseEntity<String>("Multiple Posts are added ",HttpStatus.OK);
		
	}
	 
	// create blog post rest api
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto dto){
		return new ResponseEntity<>(postService.createPost(dto),HttpStatus.CREATED);
	}
	
	// get all posts by api
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
			@RequestParam(value = "pageSize",defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstant.DEFAULT_SORT_BY, required = false)String sortBy,
			@RequestParam(value= "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false)String sortDir){
		return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
	}

	//get post by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id) {
		 return  ResponseEntity.ok(postService.getPostById(id));
	}
	
	// update post by id
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updateById(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
		
		PostDto updatedPost=postService.updatePost(postDto, id);
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
	}
	
	// deleting the post by id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable(name="id") long id){
		
		postService.deletPostById(id);
		
		return new ResponseEntity<>("Post entity deleted by id="+id+" successfully ",HttpStatus.OK);
		
	}


}
