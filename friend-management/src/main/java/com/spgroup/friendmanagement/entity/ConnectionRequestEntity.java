package com.spgroup.friendmanagement.entity;

import java.util.List;

import javax.validation.constraints.Size;

public class ConnectionRequestEntity {

	@Size(max=2, min=2)
	List<String> friends;

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
}
