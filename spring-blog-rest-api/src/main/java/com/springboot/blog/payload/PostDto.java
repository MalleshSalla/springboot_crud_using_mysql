package com.springboot.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostDto {
	
	private long id;
	
	// title should not be null or empty
	// title should have at least 2 charecter
	@NotEmpty
	@Size(min = 2,message = "Post title should have at least 2 chareters")
	private String title;

	// description should not be null or empty
	// description should have at least 10 charecter
	@NotEmpty
	@Size(min = 10,message = "Post discription have at least 10 charecters")
	private String description;
	
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;

}
