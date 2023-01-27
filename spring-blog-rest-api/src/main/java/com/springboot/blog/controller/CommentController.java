package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.service.CommentService;

import jakarta.persistence.Id;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {
	

	private CommentService commentService;
	
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,@PathVariable(value="postId")long postId){
		
		return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED); 
	}
	
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentByPostId(@PathVariable(value="postId") long postId){
		return commentService.getCommentsByPostId(postId);
	}
	
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentsById(@PathVariable(value="postId")Long postId
			,@PathVariable(value="commentId")Long commentId){
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto,HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateCommentById(@Valid @PathVariable(value="postId") Long postId,
			@PathVariable(value="id") Long commentId,@RequestBody CommentDto commentDto){
	
		CommentDto updatedComment = commentService.updateCommentById(postId, commentId, commentDto);
		
		return new ResponseEntity<CommentDto>(updatedComment,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "id")Long commentId) {
		
		commentService.deleteCommentById(postId,commentId);
		return new ResponseEntity<String>("comment deleted successfully",HttpStatus.OK);
		
	}
}
