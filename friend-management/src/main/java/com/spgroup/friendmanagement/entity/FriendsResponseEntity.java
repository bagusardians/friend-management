package com.spgroup.friendmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Api(value = "Friends Response")
public class FriendsResponseEntity extends BasicResponseEntity {

	@ApiModelProperty(value = "List of friends", required = true)
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
