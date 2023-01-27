package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	
	// we don't need have to use the @Autowired annotation here because 
	//CommentServiceImpl class we configured as spring bean and whether it has only one contructor,
	// then we ommit(remove) @Autowired
	// spring will automatically detect and inject all the nessasary dependencies
	
	
	public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository=postRepository;
		this.mapper=mapper;
	}
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Comment comment = mapToEntity(commentDto);
		
		Post post = postRepository.findById(postId)
				                 .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		comment.setPost(post);
		
		Comment newComment = commentRepository.save(comment);
		
		return mapToDto(newComment);
	}
	
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// retrieve comments by postID
		List<Comment> comments = commentRepository.findByPostId(postId);
		// convert list of comment entities to list of comment dto's
		return comments.stream().map(comment-> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "id",postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if(comment.getPost().getId() != (post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belongs to post");
		}
		
		return mapToDto(comment);
	}
	
	@Override
	public CommentDto updateCommentById(Long postId, long commentId, CommentDto commentRequest) {
		
		Post post = postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "id",postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if (comment.getPost().getId()!=(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belongs to post");
		}
		
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(comment.getBody());
		
		Comment updateComment = commentRepository.save(comment);
		
		
		return mapToDto(updateComment);
	}
	
	
	@Override
	public void deleteCommentById(Long postId, Long commentId) {
	
		Post post = postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "id",postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		
		if (comment.getPost().getId()!=(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comments does not belongs to post");
		}
		
		commentRepository.delete(comment);
	}
	
	private CommentDto mapToDto(Comment comment) {
		
		CommentDto commnetDto = mapper.map(comment,CommentDto.class);
		
		/*
		 * CommentDto commentDto = new CommentDto(); commentDto.setId(comment.getId());
		 * commentDto.setName(comment.getName());
		 * commentDto.setEmail(comment.getEmail());
		 * commentDto.setBody(comment.getBody());
		 */
			
		return commnetDto;
	}
	private Comment mapToEntity(CommentDto commentDto) {
		
		/*     // converting Dto to Entity by using constructor
		 * 
		 * Comment comment = new Comment(commentDto.getId(), commentDto.getName(),
		 * commentDto.getEmail(), commentDto.getBody());
		 */
		
		
		/*        // converting Dto to entity by using setter methods
		 * 
		 * Comment comment = new Comment(); comment.setId(commentDto.getId());
		 * comment.setName(commentDto.getName());
		 * comment.setEmail(commentDto.getEmail());
		 * comment.setBody(commentDto.getBody());
		 */
		
		Comment comment = mapper.map(commentDto, Comment.class);
		return comment;
	}

	

	

	
}
