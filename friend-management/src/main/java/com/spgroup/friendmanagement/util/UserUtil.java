package com.spgroup.friendmanagement.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import com.spgroup.friendmanagement.dto.UserDto;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;

public class UserUtil {

	private UserUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String getFirstEmail(ConnectionRequestEntity request) {
		if (Objects.isNull(request) || CollectionUtils.isEmpty(request.getFriends())) {
			throw new FriendServiceException("Invalid request entity", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			return request.getFriends().get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new FriendServiceException("Error in retrieving first email", HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	public static String getSecondEmail(ConnectionRequestEntity request) {
		if (Objects.isNull(request) || CollectionUtils.isEmpty(request.getFriends())) {
			throw new FriendServiceException("Invalid request entity", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			return request.getFriends().get(1);
		} catch (IndexOutOfBoundsException e) {
			throw new FriendServiceException("Error in retrieving second email", HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	public static List<UserDto> convertConnectionEntityToUserDtoList(ConnectionRequestEntity request) {
		RequestValidationUtil.validateConnectionRequest(request);
		List<UserDto> userDtoList = new ArrayList<>();
		for (String email : request.getFriends()) {
			UserDto userDto = new UserDto(email);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

}
