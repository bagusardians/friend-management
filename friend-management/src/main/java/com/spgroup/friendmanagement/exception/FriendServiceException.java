package com.spgroup.friendmanagement.exception;

import org.springframework.http.HttpStatus;

public class FriendServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6989393105794403624L;

	private final String errorMessage;

	private final HttpStatus errorStatus;

	public FriendServiceException(String errorMessage, HttpStatus errorStatus) {
		super();
		this.errorMessage = errorMessage;
		this.errorStatus = errorStatus;
	}

	public FriendServiceException(String errorMessage, HttpStatus errorStatus, IndexOutOfBoundsException e) {
		super(e);
		this.errorMessage = errorMessage;
		this.errorStatus = errorStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public HttpStatus getErrorStatus() {
		return errorStatus;
	}

}
