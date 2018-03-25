package com.spgroup.friendmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.spgroup.friendmanagement.dao.UserDao;
import com.spgroup.friendmanagement.dao.UserRelationDao;
import com.spgroup.friendmanagement.dto.UserDto;
import com.spgroup.friendmanagement.dto.UserRelationDto;
import com.spgroup.friendmanagement.dto.UserRelationKey;
import com.spgroup.friendmanagement.entity.BasicResponseEntity;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsResponseEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;
import com.spgroup.friendmanagement.util.RequestValidationUtil;
import com.spgroup.friendmanagement.util.UserUtil;

@Service
public class FriendManagementServiceImpl implements FriendManagementService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRelationDao userRelationDao;

	@Override
	public BasicResponseEntity createFriendConnection(ConnectionRequestEntity entity) {
		RequestValidationUtil.validateConnectionRequest(entity);

		UserDto firstUser = userDao.addUser(new UserDto(UserUtil.getFirstEmail(entity)));
		UserDto secondUser = userDao.addUser(new UserDto(UserUtil.getSecondEmail(entity)));

		UserRelationKey relationFirstKey = new UserRelationKey(firstUser.getId(), secondUser.getId());
		userRelationDao.addUserRelation(new UserRelationDto(relationFirstKey, "FRIEND", false));
		UserRelationKey relationSecondKey = new UserRelationKey(secondUser.getId(), firstUser.getId());
		userRelationDao.addUserRelation(new UserRelationDto(relationSecondKey, "FRIEND", false));

		BasicResponseEntity response = new BasicResponseEntity();
		response.setSuccess(true);
		return response;
	}

	@Override
	public FriendsResponseEntity getFriendList(FriendsRequestEntity request) {
		RequestValidationUtil.validateFriendsRequest(request);
		UserDto user = userDao.fetchUserByEmail(request.getEmail());
		if (Objects.isNull(user)) {
			throw new FriendServiceException("Cannot find the specified user", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		List<UserRelationDto> userRelationList = userRelationDao.fetchUserRelationList(user.getId());
		if (CollectionUtils.isEmpty(userRelationList)) {
			return FriendsResponseEntity.createEmptyFriendList();
		}

		List<String> friendIdList = new ArrayList<>();
		for (UserRelationDto userRelation : userRelationList) {
			friendIdList.add(userRelation.getKey().getRelatedId());
		}

		List<UserDto> userList = userDao.fetchUsersByIds(friendIdList);
		if (CollectionUtils.isEmpty(userList)) {
			return FriendsResponseEntity.createEmptyFriendList();
		}

		List<String> emailList = new ArrayList<>();
		for (UserDto userDto : userList) {
			emailList.add(userDto.getEmail());
		}
		FriendsResponseEntity response = new FriendsResponseEntity();
		response.setSuccess(true);
		response.setFriends(emailList);
		response.setCount(emailList.size());
		return response;
	}

	@Override
	public FriendsResponseEntity getCommonFriendList(ConnectionRequestEntity request) {
		return FriendsResponseEntity.createEmptyFriendList();
	}

}
