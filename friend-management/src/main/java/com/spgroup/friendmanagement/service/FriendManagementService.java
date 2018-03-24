package com.spgroup.friendmanagement.service;

import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.ConnectionResponseEntity;

@Service
public interface FriendManagementService {

	/**
	 * Make a connection between two user with email address
	 * 
	 * @param entity
	 * @return
	 */
	public ConnectionResponseEntity createFriendConnection(ConnectionRequestEntity entity);
}
