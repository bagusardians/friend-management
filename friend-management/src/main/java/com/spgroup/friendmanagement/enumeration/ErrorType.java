package com.spgroup.friendmanagement.enumeration;

import org.springframework.http.HttpStatus;

import com.spgroup.friendmanagement.entity.ErrorEntity;

import lombok.Getter;

@Getter
public enum ErrorType {

	NULL_REQUEST_ENTITY("Request entity is null", "Invalid input(s)", "422E001", HttpStatus.UNPROCESSABLE_ENTITY),
	EMPTY_FRIEND_PARAM("Friend List must not be empty", "Invalid input(s)", "422E002", HttpStatus.UNPROCESSABLE_ENTITY),
	INVALID_FRIEND_SIZE_PARAM("Friend List must contain 2 email", "Invalid input(s)", "422E003", HttpStatus.UNPROCESSABLE_ENTITY),
	SAME_EMAIL_EXIST_PARAM("Friend List must contains different email", "Invalid input(s)", "422E004", HttpStatus.UNPROCESSABLE_ENTITY),
	INVALID_EMAIL_FORMAT("Invalid email format", "Invalid input(s)", "422E005", HttpStatus.UNPROCESSABLE_ENTITY),
	REQUESTOR_EMPTY("Subscribe requestor email is empty", "Invalid input(s)", "422E006", HttpStatus.UNPROCESSABLE_ENTITY),
	TARGET_EMPTY("Subscribe target email is empty", "Invalid input(s)", "422E007", HttpStatus.UNPROCESSABLE_ENTITY),
	SUBSCRIBE_ITSELF("Requestor cannor subscribe itself", "Invalid input(s)", "422E008", HttpStatus.UNPROCESSABLE_ENTITY),
	USER_NOT_FOUND("Cannot find the specified user", "Invalid input(s)", "422E009",HttpStatus.UNPROCESSABLE_ENTITY),
	REQUESTOR_NOT_FOUND("Cannot find the specified requestor", "Invalid input(s)", "422E010",HttpStatus.UNPROCESSABLE_ENTITY),
	TARGET_NOT_FOUND("Cannot find the specified target", "Invalid input(s)", "422E011",HttpStatus.UNPROCESSABLE_ENTITY),
	BLOCKED("Users are in block relation", "Unable to make connections.", "422E012",HttpStatus.UNPROCESSABLE_ENTITY),
	SENDER_NOT_FOUND("Cannot find the specified sender", "Invalid input(s)", "422E013",HttpStatus.UNPROCESSABLE_ENTITY),
	ERROR_FIRST_EMAIL("Error in extracting first email", "Oops.. Something unexpected happen.", "500E002",HttpStatus.INTERNAL_SERVER_ERROR),
	ERROR_SECOND_EMAIL("Error in extracting second email", "Oops.. Something unexpected happen.",  "500E003",HttpStatus.INTERNAL_SERVER_ERROR);

	private String developerMessage;
	private String userMessage;
	private String code;
	private HttpStatus errorStatus;
	private ErrorEntity error;

	private ErrorType(String developerMessage, String userMessage, String code, HttpStatus errorStatus) {
		this.developerMessage = developerMessage;
		this.userMessage = userMessage;
		this.code = code;
		this.errorStatus = errorStatus;

	}

}
