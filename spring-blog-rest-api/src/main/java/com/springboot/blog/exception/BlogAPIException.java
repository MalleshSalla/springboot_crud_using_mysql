package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{
	
	private HttpStatus status;
	private String message;
	
	public BlogAPIException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
	public BlogAPIException(String massage,HttpStatus status,String message1) {
		super(massage);
		this.status= status;
		this.message=message1;
		
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	@Override
	public String getMessage() {
		return message;
	}

}
