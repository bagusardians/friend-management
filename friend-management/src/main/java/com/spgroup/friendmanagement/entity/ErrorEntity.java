package com.spgroup.friendmanagement.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ErrorEntity {

	@ApiModelProperty(value = "Developer message")
	private String developerMessage;

	@ApiModelProperty(value = "User message")
	private String userMessage;

	@ApiModelProperty(value = "Error code")
	private String code;

}
