package com.spgroup.friendmanagement.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnidirectionalRequestEntity {

	@ApiModelProperty(value = "Requestor email", required = true, example = "andy@example.com")
	private String requestor;

	@ApiModelProperty(value = "target email", required = true, example = "bagus@example.com")
	private String target;
}
