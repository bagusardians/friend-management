package com.spgroup.friendmanagement.entity;

import lombok.Data;

@Data
public class ExceptionResponseEntity {

	private boolean success;

	private ErrorEntity error;

	public static ExceptionResponseEntity createErrorEntity(ErrorEntity error) {
		ExceptionResponseEntity entity = new ExceptionResponseEntity();
		entity.setError(error);
		entity.setSuccess(false);
		return entity;
	}

}
