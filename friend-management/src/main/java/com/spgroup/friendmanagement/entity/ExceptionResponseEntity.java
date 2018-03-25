package com.spgroup.friendmanagement.entity;

public class ExceptionResponseEntity {

	private boolean success;

	private ErrorEntity error;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorEntity getError() {
		return error;
	}

	public void setError(ErrorEntity error) {
		this.error = error;
	}

	public static ExceptionResponseEntity createErrorEntity(ErrorEntity error) {
		ExceptionResponseEntity entity = new ExceptionResponseEntity();
		entity.setError(error);
		entity.setSuccess(false);
		return entity;
	}

}
