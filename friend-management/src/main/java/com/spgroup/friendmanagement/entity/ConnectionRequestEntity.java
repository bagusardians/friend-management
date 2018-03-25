package com.spgroup.friendmanagement.entity;

import java.util.List;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConnectionRequestEntity {

	@Size(max = 2, min = 2)
	@ApiModelProperty(value = "Friend List", required = true)
	List<String> friends;
}
