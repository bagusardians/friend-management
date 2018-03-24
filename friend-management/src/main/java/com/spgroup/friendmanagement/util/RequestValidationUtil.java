package com.spgroup.friendmanagement.util;

import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;

public class RequestValidationUtil {

	private RequestValidationUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void validateConnectionRequest(ConnectionRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException("Request entity is null");
		}
		List<String> friends = entity.getFriends();
		if (CollectionUtils.isEmpty(friends)) {
			throw new FriendServiceException("Friend List must not be empty");
		} else if (friends.size() != 2) {
			throw new FriendServiceException("Friend List must contain 2 email");
		} else if (friends.get(0).equals(friends.get(1))) {
			throw new FriendServiceException("Friend List must contains different email");
		} else if (!EmailValidationUtil.isEmailsValid(friends.get(0), friends.get(1))) {
			throw new FriendServiceException("Invalid email format");
		}
	}

}
