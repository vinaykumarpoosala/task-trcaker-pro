package com.vinay.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex, WebRequest request) {

		ProjectIdExceptionResponse response = new ProjectIdExceptionResponse(ex.getMessage());

		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public final ResponseEntity<Object> handleBackLogException(BacklogNotFoundException ex, WebRequest request) {

		BackLogExceptionResponse response = new BackLogExceptionResponse(ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public final ResponseEntity<Object> hadleUsernameException(UserNameAlreadyExistsException ex, WebRequest request) {

		UserNameAlreadyExistsResponse response = new UserNameAlreadyExistsResponse(ex.getMessage());

		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
}
