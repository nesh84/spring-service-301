package com.eatza.review.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);
	
	@ExceptionHandler(ReviewException.class)
	public ResponseEntity<Object> exception(ReviewException exception) {
		logger.debug("Handling ReviewException");
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	
	
	
}
