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
import com.spgroup.friendmanagement.entity.RecipientsResponseEntity;
import com.spgroup.friendmanagement.entity.UnidirectionalRequestEntity;
import com.spgroup.friendmanagement.entity.UpdateRequestEntity;
import com.spgroup.friendmanagement.service.FriendManagementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Friend Management")
@Controller
public class FriendManagementController {

	@Autowired
	FriendManagementService friendManagementService;

	@ApiOperation(value = "Make friends connection", notes = "return true if success", response = BasicResponseEntity.class, httpMethod = "POST")
	@RequestMapping(value = "/connect/friend", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BasicResponseEntity> createFriendConnection(@RequestBody ConnectionRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.createFriendConnection(request));
	}

	@ApiOperation(value = "Make subscribe connection", notes = "return true if success", response = BasicResponseEntity.class, httpMethod = "POST")
	@RequestMapping(value = "/connect/subscribe", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BasicResponseEntity> createSubscribeConnection(
			@RequestBody UnidirectionalRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.createSubscribeConnection(request));
	}

	@ApiOperation(value = "Block updates of user", notes = "return true if success", response = BasicResponseEntity.class, httpMethod = "POST")
	@RequestMapping(value = "/connect/block", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BasicResponseEntity> blockUpdates(@RequestBody UnidirectionalRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.blockUpdates(request));
	}

	@ApiOperation(value = "Retrieve list of friends", notes = "return list of friends's email", response = BasicResponseEntity.class, httpMethod = "POST")
	@RequestMapping(value = "/friends", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FriendsResponseEntity> getFriendList(@RequestBody FriendsRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.getFriendList(request));
	}

	@ApiOperation(value = "Get friends in common", notes = "return list of friend in common", response = BasicResponseEntity.class, httpMethod = "POST")
	@RequestMapping(value = "/friends/common", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FriendsResponseEntity> getCommonFriendList(@RequestBody ConnectionRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.getCommonFriendList(request));
	}

	@ApiOperation(value = "Retrive list of update's recipient", notes = "return list of email who get updates", response = BasicResponseEntity.class, httpMethod = "POST")
	@RequestMapping(value = "/recipients/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<RecipientsResponseEntity> getRecipientsOfUpdate(@RequestBody UpdateRequestEntity request) {
		return ResponseEntity.ok(friendManagementService.getRecipientsOfUpdate(request));
	}
}
