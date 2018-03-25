package com.spgroup.friendmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.dao.UserDao;
import com.spgroup.friendmanagement.dao.UserRelationDao;
import com.spgroup.friendmanagement.dto.UserDto;
import com.spgroup.friendmanagement.dto.UserRelationDto;
import com.spgroup.friendmanagement.dto.UserRelationKey;
import com.spgroup.friendmanagement.entity.BasicResponseEntity;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsResponseEntity;
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
		FriendsResponseEntity response = new FriendsResponseEntity();
		response.setSuccess(true);
		return response;
	}

}
