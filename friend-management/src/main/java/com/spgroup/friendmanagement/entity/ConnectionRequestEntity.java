package com.spgroup.friendmanagement.entity;

import java.util.List;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ConnectionRequestEntity {

	@Size(max = 2, min = 2)
	List<String> friends;
}
