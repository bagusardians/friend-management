package com.spgroup.friendmanagement.entity;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConnectionRequestEntity {

	@ApiModelProperty(value = "Friend List", required = true, allowEmptyValue = false)
	List<String> friends;
}
