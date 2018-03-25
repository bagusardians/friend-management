package com.spgroup.friendmanagement.util;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;

public class RequestValidationUtil {

	private RequestValidationUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void validateConnectionRequest(ConnectionRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException("Request entity is null", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		List<String> friends = entity.getFriends();
		if (CollectionUtils.isEmpty(friends)) {
			throw new FriendServiceException("Friend List must not be empty", HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (friends.size() != 2) {
			throw new FriendServiceException("Friend List must contain 2 email", HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (friends.get(0).equals(friends.get(1))) {
			throw new FriendServiceException("Friend List must contains different email",
					HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (!EmailValidationUtil.isEmailsValid(friends.get(0), friends.get(1))) {
			throw new FriendServiceException("Invalid email format", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
