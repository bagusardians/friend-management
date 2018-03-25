package com.spgroup.friendmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spgroup.friendmanagement.entity.BasicResponseEntity;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsResponseEntity;
import com.spgroup.friendmanagement.service.FriendManagementService;

@Controller
public class FriendManagementController {

	@Autowired
	FriendManagementService friendManagementService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public boolean check() {
		return true;
	}

	@RequestMapping(value = "/connect", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<BasicResponseEntity> createFriendConnection(@RequestBody ConnectionRequestEntity entity) {
		return ResponseEntity.ok(friendManagementService.createFriendConnection(entity));
	}

	@RequestMapping(value = "/friends", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FriendsResponseEntity> getFriendList(@RequestBody FriendsRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.getFriendList(request));
	}

	@RequestMapping(value = "/friends/common", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FriendsResponseEntity> getCommonFriendList(@RequestBody ConnectionRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.getCommonFriendList(request));
	}
}
