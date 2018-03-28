package com.spgroup.friendmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.spgroup.friendmanagement.enumeration.ErrorType;
import com.spgroup.friendmanagement.enumeration.RelationTypeEnum;
import com.spgroup.friendmanagement.exception.FriendServiceException;
import com.spgroup.friendmanagement.util.EmailUtil;
import com.spgroup.friendmanagement.util.RequestValidationUtil;
import com.spgroup.friendmanagement.util.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FriendManagementServiceImpl implements FriendManagementService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRelationDao userRelationDao;

	@Override
	public BasicResponseEntity createFriendConnection(ConnectionRequestEntity entity) {
		log.info("Create a friend connection");
		RequestValidationUtil.validateConnectionRequest(entity);

		UserDto firstUser = userDao.addUser(new UserDto(UserUtil.getFirstEmail(entity)));
		UserDto secondUser = userDao.addUser(new UserDto(UserUtil.getSecondEmail(entity)));

		UserRelationDto userRelationFirst = userRelationDao.fetchCorrelationBetweenTwoUser(firstUser.getId(),
				secondUser.getId());
		UserRelationDto userRelationSecond = userRelationDao.fetchCorrelationBetweenTwoUser(secondUser.getId(),
				firstUser.getId());

		if (isBlocked(userRelationFirst, userRelationSecond)) {
			log.info("unable to add friend");
			throw new FriendServiceException(ErrorType.BLOCKED);
		}

		UserRelationKey relationFirstKey = new UserRelationKey(firstUser.getId(), secondUser.getId());
		userRelationDao.addUserRelation(new UserRelationDto(relationFirstKey, RelationTypeEnum.FRIEND, false));
		UserRelationKey relationSecondKey = new UserRelationKey(secondUser.getId(), firstUser.getId());
		userRelationDao.addUserRelation(new UserRelationDto(relationSecondKey, RelationTypeEnum.FRIEND, false));

		log.info("successfully create a friend connection");
		return BasicResponseEntity.createSuccessResponse();
	}

	private boolean isBlocked(UserRelationDto userRelationFirst, UserRelationDto userRelationSecond) {
		return ((!Objects.isNull(userRelationFirst) && userRelationFirst.isBlock())
				|| (!Objects.isNull(userRelationSecond) && userRelationSecond.isBlock()));
	}

	@Override
	public FriendsResponseEntity getFriendList(FriendsRequestEntity request) {
		log.info("Retrieving friend list");
		RequestValidationUtil.validateFriendsRequest(request);
		UserDto user = userDao.fetchUserByEmail(request.getEmail());
		if (Objects.isNull(user)) {
			throw new FriendServiceException(ErrorType.USER_NOT_FOUND);
		}

		List<UserRelationDto> userRelationList = userRelationDao.fetchUserRelationList(user.getId());
		if (CollectionUtils.isEmpty(userRelationList)) {
			log.info("No friend list");
			return FriendsResponseEntity.createEmptyFriendList();
		}

		List<String> friendIdList = new ArrayList<>();
		for (UserRelationDto userRelation : userRelationList) {
			friendIdList.add(userRelation.getKey().getRelatedId());
		}

		List<UserDto> userList = userDao.fetchUsersByIds(friendIdList);
		if (CollectionUtils.isEmpty(userList)) {
			log.info("No friend list");
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
		log.info("Friend list successfully retrieved");
		return response;
	}

	@Override
	public FriendsResponseEntity getCommonFriendList(ConnectionRequestEntity request) {
		log.info("Retrieving common friend list");
		RequestValidationUtil.validateConnectionRequest(request);

		// get UserDto of the inputs and validate
		UserDto firstUser = userDao.fetchUserByEmail(UserUtil.getFirstEmail(request));
		UserDto secondUser = userDao.fetchUserByEmail(UserUtil.getSecondEmail(request));
		validateBasicRetrievedUsers(firstUser, secondUser);

		// get the User relation for these user dto and find the commonality
		List<String> firstUserRelationList = UserUtil
				.convertUserRelationListToRelatedIdList(userRelationDao.fetchUserRelationList(firstUser.getId()));
		List<String> secondUserRelationList = UserUtil
				.convertUserRelationListToRelatedIdList(userRelationDao.fetchUserRelationList(secondUser.getId()));
		if (CollectionUtils.isEmpty(firstUserRelationList) || CollectionUtils.isEmpty(secondUserRelationList)) {
			log.info("No common friend list");
			return FriendsResponseEntity.createEmptyFriendList();
		}
		firstUserRelationList.retainAll(secondUserRelationList);

		List<UserDto> userList = userDao.fetchUsersByIds(firstUserRelationList);
		if (CollectionUtils.isEmpty(userList)) {
			log.info("No common friend list");
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
		log.info("Common friend list successfully retrieved");
		return response;
	}

	private void validateBasicRetrievedUsers(UserDto firstUser, UserDto secondUser) {
		if (Objects.isNull(firstUser) || Objects.isNull(secondUser)) {
			throw new FriendServiceException(ErrorType.USER_NOT_FOUND);
		}
	}

	private void validateUniRetrievedUsers(UserDto requestor, UserDto target) {
		if (Objects.isNull(requestor)) {
			throw new FriendServiceException(ErrorType.REQUESTOR_NOT_FOUND);
		}
		if (Objects.isNull(target)) {
			throw new FriendServiceException(ErrorType.TARGET_NOT_FOUND);
		}
	}

	@Override
	public BasicResponseEntity createSubscribeConnection(UnidirectionalRequestEntity request) {
		log.info("Creates a subscribe connection");
		RequestValidationUtil.validateSubscribeRequest(request);

		UserDto requestor = userDao.addUser(new UserDto(request.getRequestor()));
		UserDto target = userDao.addUser(new UserDto(request.getTarget()));

		UserRelationKey relationFirstKey = new UserRelationKey(requestor.getId(), target.getId());
		userRelationDao.addUserRelation(new UserRelationDto(relationFirstKey, RelationTypeEnum.SUBSCRIBE, false));
		log.info("Subscription successfully created");
		return BasicResponseEntity.createSuccessResponse();
	}

	@Override
	public BasicResponseEntity blockUpdates(UnidirectionalRequestEntity request) {
		log.info("Block updates of user");
		RequestValidationUtil.validateSubscribeRequest(request);

		UserDto requestor = userDao.fetchUserByEmail(request.getRequestor());
		UserDto target = userDao.fetchUserByEmail(request.getTarget());
		validateUniRetrievedUsers(requestor, target);

		UserRelationDto userRelation = userRelationDao.fetchCorrelationBetweenTwoUser(requestor.getId(),
				target.getId());
		if (Objects.isNull(userRelation)) {
			UserRelationKey relationKey = new UserRelationKey(requestor.getId(), target.getId());
			userRelation = new UserRelationDto(relationKey, RelationTypeEnum.BLOCK, true);
		}

		userRelation.setBlock(true);
		userRelationDao.addUserRelation(userRelation);

		log.info("Block updates successfully performed");
		return BasicResponseEntity.createSuccessResponse();
	}

	@Override
	public RecipientsResponseEntity getRecipientsOfUpdate(UpdateRequestEntity request) {
		log.info("Get recipient list of updates");
		RequestValidationUtil.validateUpdateRequest(request);
		UserDto user = userDao.fetchUserByEmail(request.getSender());
		if (Objects.isNull(user)) {
			throw new FriendServiceException(ErrorType.SENDER_NOT_FOUND);
		}
		List<UserRelationDto> relationList = userRelationDao.fetchUserRelationListByRelatedId(user.getId());
		if (CollectionUtils.isEmpty(relationList)) {
			log.info("No recipient");
			return RecipientsResponseEntity.createEmptyRecipientList();
		}

		List<String> recipientIdList = new ArrayList<>();
		for (UserRelationDto relation : relationList) {
			if (!relation.isBlock()) {
				recipientIdList.add(relation.getKey().getId());
			}
		}
		if (CollectionUtils.isEmpty(recipientIdList)) {
			log.info("No recipient");
			return RecipientsResponseEntity.createEmptyRecipientList();
		}

		List<UserDto> userListFromText = getRecipientsFromText(request, user);

		List<UserDto> userList = userDao.fetchUsersByIds(recipientIdList);
		userList.removeAll(userListFromText);
		userList.addAll(userListFromText);
		if (CollectionUtils.isEmpty(userList)) {
			log.info("No recipient");
			return RecipientsResponseEntity.createEmptyRecipientList();
		}

		List<String> emailList = new ArrayList<>();
		for (UserDto userDto : userList) {
			emailList.add(userDto.getEmail());
		}

		RecipientsResponseEntity response = new RecipientsResponseEntity();
		response.setSuccess(true);
		response.setRecipients(emailList);
		log.info("Recipient list successfully retrieved");
		return response;
	}

	private List<UserDto> getRecipientsFromText(UpdateRequestEntity request, UserDto user) {
		log.info("Extracting email from update text");
		List<String> emailListFromText = EmailUtil.extractEmailFromText(request.getText());
		List<UserDto> recipientFromText = new ArrayList<>();
		if (CollectionUtils.isEmpty(emailListFromText)) {
			log.info("No email in update text");
			return recipientFromText;
		}
		List<UserDto> userListFromText = userDao.fetchUserByEmail(emailListFromText);
		for (UserDto userDto : userListFromText) {
			UserRelationDto relation = userRelationDao.fetchCorrelationBetweenTwoUser(userDto.getId(), user.getId());
			if (Objects.isNull(relation) || !relation.isBlock()) {
				recipientFromText.add(userDto);
			}
		}
		log.info("email successfully extracted from text");
		return recipientFromText;
	}

}
