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
import com.spgroup.friendmanagement.entity.RecipientsResponseEntity;
import com.spgroup.friendmanagement.entity.UnidirectionalRequestEntity;
import com.spgroup.friendmanagement.entity.UpdateRequestEntity;
import com.spgroup.friendmanagement.enumeration.RelationTypeEnum;
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
		userRelationDao.addUserRelation(new UserRelationDto(relationFirstKey, RelationTypeEnum.FRIEND, false));
		UserRelationKey relationSecondKey = new UserRelationKey(secondUser.getId(), firstUser.getId());
		userRelationDao.addUserRelation(new UserRelationDto(relationSecondKey, RelationTypeEnum.FRIEND, false));

		return BasicResponseEntity.createSuccessResponse();
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
		RequestValidationUtil.validateConnectionRequest(request);

		// get UserDto of the inputs and validate
		UserDto firstUser = userDao.fetchUserByEmail(UserUtil.getFirstEmail(request));
		UserDto secondUser = userDao.fetchUserByEmail(UserUtil.getSecondEmail(request));
		validateRetrievedUsers(request, firstUser, secondUser);

		// get the User relation for these user dto and find the commonality
		List<String> firstUserRelationList = UserUtil
				.convertUserRelationListToRelatedIdList(userRelationDao.fetchUserRelationList(firstUser.getId()));
		List<String> secondUserRelationList = UserUtil
				.convertUserRelationListToRelatedIdList(userRelationDao.fetchUserRelationList(secondUser.getId()));
		if (CollectionUtils.isEmpty(firstUserRelationList) || CollectionUtils.isEmpty(secondUserRelationList)) {
			return FriendsResponseEntity.createEmptyFriendList();
		}
		firstUserRelationList.retainAll(secondUserRelationList);

		List<UserDto> userList = userDao.fetchUsersByIds(firstUserRelationList);
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

	private void validateRetrievedUsers(ConnectionRequestEntity request, UserDto firstUser, UserDto secondUser) {
		if (Objects.isNull(firstUser)) {
			throw new FriendServiceException(
					"Cannot find the specified user with email: " + UserUtil.getFirstEmail(request),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (Objects.isNull(secondUser)) {
			throw new FriendServiceException(
					"Cannot find the specified user with email: " + UserUtil.getSecondEmail(request),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	private void validateRetrievedUsers(UnidirectionalRequestEntity request, UserDto requestor, UserDto target) {
		if (Objects.isNull(requestor)) {
			throw new FriendServiceException("Cannot find the specified user with email: " + request.getRequestor(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (Objects.isNull(target)) {
			throw new FriendServiceException("Cannot find the specified user with email: " + request.getTarget(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public BasicResponseEntity createSubscribeConnection(UnidirectionalRequestEntity request) {
		RequestValidationUtil.validateSubscribeRequest(request);

		UserDto requestor = userDao.addUser(new UserDto(request.getRequestor()));
		UserDto target = userDao.addUser(new UserDto(request.getTarget()));

		UserRelationKey relationFirstKey = new UserRelationKey(requestor.getId(), target.getId());
		userRelationDao.addUserRelation(new UserRelationDto(relationFirstKey, RelationTypeEnum.SUBSCRIBE, false));
		return BasicResponseEntity.createSuccessResponse();
	}

	@Override
	public BasicResponseEntity blockUpdates(UnidirectionalRequestEntity request) {
		RequestValidationUtil.validateSubscribeRequest(request);

		UserDto requestor = userDao.fetchUserByEmail(request.getRequestor());
		UserDto target = userDao.fetchUserByEmail(request.getTarget());
		validateRetrievedUsers(request, requestor, target);

		UserRelationDto userRelation = userRelationDao.fetchCorrelationBetweenTwoUser(requestor.getId(),
				target.getId());
		if (Objects.isNull(userRelation)) {
			throw new FriendServiceException("No connection from requestor to target", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		userRelation.setBlock(true);
		userRelationDao.addUserRelation(userRelation);

		return BasicResponseEntity.createSuccessResponse();
	}

	@Override
	public RecipientsResponseEntity getRecipientsOfUpdate(UpdateRequestEntity request) {
		RequestValidationUtil.validateUpdateRequest(request);
		UserDto user = userDao.fetchUserByEmail(request.getSender());
		if (Objects.isNull(user)) {
			throw new FriendServiceException("Cannot find the specified sender", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		List<String> relationList = UserUtil
				.convertUserRelationListToRelatedIdList(userRelationDao.fetchUserRelationList(user.getId()));
		if (CollectionUtils.isEmpty(relationList)) {
			return RecipientsResponseEntity.createEmptyRecipientList();
		}
		RecipientsResponseEntity response = new RecipientsResponseEntity();
		response.setSuccess(true);
		return response;
	}

}
