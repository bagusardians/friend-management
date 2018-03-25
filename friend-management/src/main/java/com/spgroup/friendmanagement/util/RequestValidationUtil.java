package com.spgroup.friendmanagement.util;

import java.util.List;
import java.util.Objects;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsRequestEntity;
import com.spgroup.friendmanagement.entity.UnidirectionalRequestEntity;
import com.spgroup.friendmanagement.entity.UpdateRequestEntity;
import com.spgroup.friendmanagement.enumeration.ErrorType;
import com.spgroup.friendmanagement.exception.FriendServiceException;

public class RequestValidationUtil {

	private RequestValidationUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void validateConnectionRequest(ConnectionRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException(ErrorType.NULL_REQUEST_ENTITY);
		}
		List<String> friends = entity.getFriends();
		if (CollectionUtils.isEmpty(friends)) {
			throw new FriendServiceException(ErrorType.EMPTY_FRIEND_PARAM);
		} else if (friends.size() != 2) {
			throw new FriendServiceException(ErrorType.INVALID_FRIEND_SIZE_PARAM);
		} else if (friends.get(0).equals(friends.get(1))) {
			throw new FriendServiceException(ErrorType.SAME_EMAIL_EXIST_PARAM);
		} else if (!EmailUtil.isEmailsValid(friends.get(0), friends.get(1))) {
			throw new FriendServiceException(ErrorType.INVALID_EMAIL_FORMAT);
		}
	}

	public static void validateFriendsRequest(FriendsRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException(ErrorType.NULL_REQUEST_ENTITY);
		}
		String email = entity.getEmail();
		if (!EmailUtil.isEmailsValid(email)) {
			throw new FriendServiceException(ErrorType.INVALID_EMAIL_FORMAT);
		}
	}

	public static void validateSubscribeRequest(UnidirectionalRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException(ErrorType.NULL_REQUEST_ENTITY);
		}
		if (StringUtils.isEmpty(entity.getRequestor())) {
			throw new FriendServiceException(ErrorType.REQUESTOR_EMPTY);
		} else if (StringUtils.isEmpty(entity.getTarget())) {
			throw new FriendServiceException(ErrorType.TARGET_EMPTY);
		} else if (entity.getRequestor().equals(entity.getTarget())) {
			throw new FriendServiceException(ErrorType.SUBSCRIBE_ITSELF);
		} else if (!EmailUtil.isEmailsValid(entity.getRequestor(), entity.getTarget())) {
			throw new FriendServiceException(ErrorType.INVALID_EMAIL_FORMAT);
		}
	}

	public static void validateUpdateRequest(UpdateRequestEntity entity) {
		if (Objects.isNull(entity)) {
			throw new FriendServiceException(ErrorType.NULL_REQUEST_ENTITY);
		}
		String email = entity.getSender();
		if (!EmailUtil.isEmailsValid(email)) {
			throw new FriendServiceException(ErrorType.INVALID_EMAIL_FORMAT);
		}
	}

}
