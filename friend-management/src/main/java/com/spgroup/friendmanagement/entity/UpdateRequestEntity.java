package com.spgroup.friendmanagement.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateRequestEntity {

	@ApiModelProperty(value = "Sender email", required = true, example = "andy@example.com")
	private String sender;

	@ApiModelProperty(value = "Free text for updates", example = "Hello world!")
	private String text;
}
