package com.spgroup.friendmanagement.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spgroup.friendmanagement.entity.ErrorEntity;
import com.spgroup.friendmanagement.entity.ExceptionResponseEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;

@ControllerAdvice
public class ExceptionControllerAdvice {

	/**
	 * handle Service Exception
	 * 
	 * @param e
	 */
	@ExceptionHandler(value = FriendServiceException.class)
	public ResponseEntity<ExceptionResponseEntity> handleFriendServiceException(FriendServiceException e) {
		ErrorEntity error = new ErrorEntity();
		error.setCode("422E001");
		error.setDeveloperMessage(e.getErrorMessage());
		error.setUserMessage("Invalid input(s).");
		return new ResponseEntity<>(ExceptionResponseEntity.createErrorEntity(error), e.getErrorStatus());
	}

	/**
	 * handle the rest of exception
	 * 
	 * @param e
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponseEntity> handleException(Exception e) {
		ErrorEntity error = new ErrorEntity();
		error.setCode("500E001");
		error.setDeveloperMessage(e.getMessage());
		error.setUserMessage("Oops.. Something unexpected happen on our side.");
		return new ResponseEntity<>(ExceptionResponseEntity.createErrorEntity(error), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
