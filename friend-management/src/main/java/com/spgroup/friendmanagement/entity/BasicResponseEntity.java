package com.spgroup.friendmanagement.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "Basic Response")
public class BasicResponseEntity {

	@ApiModelProperty(value = "Is operation success")
	boolean success;

	public BasicResponseEntity() {
	}

	public BasicResponseEntity(boolean success) {
		this.success = success;
	}

	public static BasicResponseEntity createSuccessResponse() {
		BasicResponseEntity response = new BasicResponseEntity();
		response.setSuccess(true);
		return response;
	}
}
