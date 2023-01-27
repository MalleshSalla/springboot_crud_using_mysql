package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper mapper;

	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
		super();
		this.postRepository = postRepository;
		this.mapper=mapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {

		// convert DTO to entity
		Post post = mapToEntity(postDto);

		Post newPost = postRepository.save(post);

		// convert entity DTO
		PostDto postResponse = mapToDto(newPost);

		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy)
				.ascending():Sort.by(sortBy).descending();

		// create Pageable instance
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<Post> posts = postRepository.findAll(pageable);

		// get content for page object
		List<Post> listOfPosts = posts.getContent();
		List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse(
				content,posts.getNumber(),posts.getSize()
				,posts.getTotalElements(),posts.getTotalPages()
				,posts.isLast());
		return postResponse;
		
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setTitle(postDto.getTitle());
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public void deletPostById(long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);

	}

	// converting entity to postDto
	private PostDto mapToDto(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);

		/*          // converting by using setting method
		 * PostDto postDto = new PostDto(); 
		 * postDto.setId(post.getId());
		 * postDto.setContent(post.getContent());
		 * postDto.setDescription(post.getDescription());
		 * postDto.setTitle(post.getTitle());
		 */

		return postDto;
	}

	// converting Dto to entity
	private Post mapToEntity(PostDto postDto) {
		
		Post post = mapper.map(postDto, Post.class);
		
		//Post post = new Post();
		// converting Dto to entity by sing constructor
		//Post post = new Post(postDto.getId(),postDto.getContent(),postDto.getDescription(),postDto.getTitle());
		
		/*           // converting by using setter method
		 * post.setId(postDto.getId()); post.setTitle(postDto.getTitle());
		 * post.setDescription(postDto.getDescription());
		 * post.setContent(postDto.getContent());
		 */
		 
		return post;
	}

	@Override
	public void createPosts(PostDto[] postDto) {

		for (PostDto postDto2 : postDto) {
			Post post = mapToEntity(postDto2);
			postRepository.save(post);
		}

	}

}
