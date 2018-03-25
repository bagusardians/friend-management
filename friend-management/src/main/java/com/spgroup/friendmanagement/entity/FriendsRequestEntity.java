package com.spgroup.friendmanagement.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FriendsRequestEntity {

	@ApiModelProperty(value = "Email address", required = true, example = "andy@example.com")
	private String email;
}
