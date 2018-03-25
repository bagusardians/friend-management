package com.spgroup.friendmanagement.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spgroup.friendmanagement.entity.ErrorEntity;
import com.spgroup.friendmanagement.entity.ExceptionResponseEntity;
import com.spgroup.friendmanagement.enumeration.ErrorType;
import com.spgroup.friendmanagement.exception.FriendServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

	/**
	 * handle Service Exception
	 * 
	 * @param e
	 */
	@ExceptionHandler(value = FriendServiceException.class)
	public ResponseEntity<ExceptionResponseEntity> handleFriendServiceException(FriendServiceException e) {
		log.error("Error found {}", e.getErrorType(), e);
		ErrorType errorType = e.getErrorType();
		ErrorEntity error = new ErrorEntity();
		error.setCode(errorType.getCode());
		error.setDeveloperMessage(errorType.getDeveloperMessage());
		error.setUserMessage(errorType.getUserMessage());
		return new ResponseEntity<>(ExceptionResponseEntity.createErrorEntity(error), errorType.getErrorStatus());
	}

	/**
	 * handle the rest of exception
	 * 
	 * @param e
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponseEntity> handleException(Exception e) {
		log.error("Unexpected Error found", e);
		ErrorEntity error = new ErrorEntity();
		error.setCode("500E001");
		error.setDeveloperMessage(e.getMessage());
		error.setUserMessage("Oops.. Something unexpected happen on our side.");
		return new ResponseEntity<>(ExceptionResponseEntity.createErrorEntity(error), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
