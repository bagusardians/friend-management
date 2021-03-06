package com.spgroup.friendmanagement.service;

import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.entity.BasicResponseEntity;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsResponseEntity;
import com.spgroup.friendmanagement.entity.RecipientsResponseEntity;
import com.spgroup.friendmanagement.entity.UnidirectionalRequestEntity;
import com.spgroup.friendmanagement.entity.UpdateRequestEntity;

@Service
public interface FriendManagementService {

	/**
	 * Make a connection between two user with email address
	 * 
	 * @param entity
	 * @return
	 */
	public BasicResponseEntity createFriendConnection(ConnectionRequestEntity entity);

	/**
	 * Get Friend List based on email
	 * 
	 * @param request
	 * @return
	 */
	public FriendsResponseEntity getFriendList(FriendsRequestEntity request);

	/**
	 * Retrieve the common friends list between two email addresses
	 * 
	 * @param request
	 * @return
	 */
	public FriendsResponseEntity getCommonFriendList(ConnectionRequestEntity request);

	/**
	 * Create a subscribe connection of first user to second user
	 * 
	 * @param entity
	 * @return
	 */
	public BasicResponseEntity createSubscribeConnection(UnidirectionalRequestEntity request);

	/**
	 * block target user udpates from requestor user
	 * 
	 * @param request
	 * @return
	 */
	public BasicResponseEntity blockUpdates(UnidirectionalRequestEntity request);

	/**
	 * get list of recipients of an update from user
	 * 
	 * @param request
	 * @return
	 */
	public RecipientsResponseEntity getRecipientsOfUpdate(UpdateRequestEntity request);
}
