package com.spgroup.friendmanagement.exception;

public class FriendServiceException extends RuntimeException{
	
	String errorMessage;

	public FriendServiceException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6989393105794403624L;

}
