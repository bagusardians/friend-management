package com.spgroup.friendmanagement.entity;

import lombok.Data;

@Data
public class BasicResponseEntity {

	boolean success;

	public BasicResponseEntity() {
	}

	public BasicResponseEntity(boolean success) {
		this.success = success;
	}
}
