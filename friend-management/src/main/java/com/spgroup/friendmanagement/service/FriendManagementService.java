package com.spgroup.friendmanagement.service;

import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.entity.BasicResponseEntity;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsResponseEntity;

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
	 * @param email
	 * @return
	 */
	public FriendsResponseEntity getFriendList(String email);
}
