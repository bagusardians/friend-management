package com.spgroup.friendmanagement.exception;

import com.spgroup.friendmanagement.enumeration.ErrorType;

import lombok.Getter;

@Getter
public class FriendServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6989393105794403624L;

	private final ErrorType errorType;

	public FriendServiceException(ErrorType errorType) {
		super();
		this.errorType = errorType;
	}

	public FriendServiceException(ErrorType errorType, Exception e) {
		super(e);
		this.errorType = errorType;
	}

}
