package com.spgroup.friendmanagement.util;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsRequestEntity;
import com.spgroup.friendmanagement.entity.UnidirectionalRequestEntity;
import com.spgroup.friendmanagement.entity.UpdateRequestEntity;
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
		} else if (!EmailUtil.isEmailsValid(friends.get(0), friends.get(1))) {
			throw new FriendServiceException("Invalid email format", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public static void validateFriendsRequest(FriendsRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException("Request entity is null", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		String email = entity.getEmail();
		if (!EmailUtil.isEmailsValid(email)) {
			throw new FriendServiceException("Invalid email format", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public static void validateSubscribeRequest(UnidirectionalRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException("Request entity is null", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (StringUtils.isEmpty(entity.getRequestor())) {
			throw new FriendServiceException("Subscribe requestor email is empty", HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (StringUtils.isEmpty(entity.getTarget())) {
			throw new FriendServiceException("Subscribe target email is empty", HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (entity.getRequestor().equals(entity.getTarget())) {
			throw new FriendServiceException("Requestor cannor subscribe itself", HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (!EmailUtil.isEmailsValid(entity.getRequestor(), entity.getTarget())) {
			throw new FriendServiceException("Invalid email format", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public static void validateUpdateRequest(UpdateRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException("Request entity is null", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		String email = entity.getSender();
		if (!EmailUtil.isEmailsValid(email)) {
			throw new FriendServiceException("Invalid email format", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
