package com.spgroup.friendmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendsResponseEntity extends BasicResponseEntity {

	List<String> friends;

	int count;

	public FriendsResponseEntity() {
		super();

	}

	public FriendsResponseEntity(boolean isSuccess, List<String> friends, int count) {
		super(isSuccess);
		this.friends = friends;
		this.count = count;

	}

	public static FriendsResponseEntity createEmptyFriendList() {
		FriendsResponseEntity response = new FriendsResponseEntity();
		response.setSuccess(true);
		response.setFriends(new ArrayList<>());
		response.setCount(0);
		return response;

	}

}
