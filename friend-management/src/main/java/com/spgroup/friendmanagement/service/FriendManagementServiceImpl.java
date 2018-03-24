package com.spgroup.friendmanagement.service;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.ConnectionResponseEntity;

public class FriendManagementServiceImpl implements FriendManagementService {

	@Override
	public ConnectionResponseEntity createFriendConnection(ConnectionRequestEntity entity) {
		ConnectionResponseEntity response = new ConnectionResponseEntity();
		response.setSuccess(true);
		return response;
	}

}
