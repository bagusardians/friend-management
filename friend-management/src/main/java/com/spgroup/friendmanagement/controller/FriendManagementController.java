package com.spgroup.friendmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.ConnectionResponseEntity;
import com.spgroup.friendmanagement.service.FriendManagementService;

@Controller
public class FriendManagementController {
	
	@Autowired
	FriendManagementService friendManagementService;

	@RequestMapping(value = "/connect", method = RequestMethod.PUT)
	public ConnectionResponseEntity createFriendConnection(ConnectionRequestEntity entity) {
		return friendManagementService.createFriendConnection(entity);
	}
}
