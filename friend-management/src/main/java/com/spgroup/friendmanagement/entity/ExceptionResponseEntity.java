package com.spgroup.friendmanagement.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExceptionResponseEntity {

	@ApiModelProperty(value = "Is operation success")
	private boolean success;

	@ApiModelProperty(value = "Error entity")
	private ErrorEntity error;

	public static ExceptionResponseEntity createErrorEntity(ErrorEntity error) {
		ExceptionResponseEntity entity = new ExceptionResponseEntity();
		entity.setError(error);
		entity.setSuccess(false);
		return entity;
	}

}
