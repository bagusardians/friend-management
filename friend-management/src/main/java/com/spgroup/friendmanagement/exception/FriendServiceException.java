package com.spgroup.friendmanagement.exception;

public class FriendServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6989393105794403624L;
	
	private final String errorMessage;

	public FriendServiceException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
