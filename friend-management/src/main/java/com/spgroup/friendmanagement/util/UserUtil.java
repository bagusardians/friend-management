package com.spgroup.friendmanagement.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.spgroup.friendmanagement.dto.UserDto;
import com.spgroup.friendmanagement.dto.UserRelationDto;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.enumeration.ErrorType;
import com.spgroup.friendmanagement.exception.FriendServiceException;

public class UserUtil {

	private UserUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * extract first email from friend list in request entity
	 * 
	 * @param request
	 * @return
	 */
	public static String getFirstEmail(ConnectionRequestEntity request) {
		if (Objects.isNull(request)) {
			throw new FriendServiceException(ErrorType.NULL_REQUEST_ENTITY);
		}
		if (CollectionUtils.isEmpty(request.getFriends())) {
			throw new FriendServiceException(ErrorType.EMPTY_FRIEND_PARAM);
		}
		try {
			return request.getFriends().get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new FriendServiceException(ErrorType.ERROR_FIRST_EMAIL, e);
		}
	}

	/**
	 * extract second email from friend list in request entity
	 * 
	 * @param request
	 * @return
	 */
	public static String getSecondEmail(ConnectionRequestEntity request) {
		if (Objects.isNull(request)) {
			throw new FriendServiceException(ErrorType.NULL_REQUEST_ENTITY);
		}
		if (CollectionUtils.isEmpty(request.getFriends())) {
			throw new FriendServiceException(ErrorType.EMPTY_FRIEND_PARAM);
		}
		try {
			return request.getFriends().get(1);
		} catch (IndexOutOfBoundsException e) {
			throw new FriendServiceException(ErrorType.ERROR_SECOND_EMAIL, e);
		}
	}

	/**
	 * convert {@link ConnectionRequestEntity} friends to list of {@link UserDto}
	 * 
	 * @param request
	 * @return
	 */
	public static List<UserDto> convertConnectionEntityToUserDtoList(ConnectionRequestEntity request) {
		RequestValidationUtil.validateConnectionRequest(request);
		List<UserDto> userDtoList = new ArrayList<>();
		for (String email : request.getFriends()) {
			UserDto userDto = new UserDto(email);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	/**
	 * Convert List of {@link UserRelationDto} to List of Related Id
	 * 
	 * @param userRelationList
	 * @return
	 */
	public static List<String> convertUserRelationListToRelatedIdList(List<UserRelationDto> userRelationList) {
		if (CollectionUtils.isEmpty(userRelationList)) {
			return Lists.newArrayList();
		}
		return userRelationList.stream().map(user -> user.getKey().getRelatedId()).collect(Collectors.toList());

	}

}
