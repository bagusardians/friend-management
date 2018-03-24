package com.spgroup.friendmanagement.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;

public class FriendManagementServiceImplTest {

	FriendManagementServiceImpl underTest = new FriendManagementServiceImpl();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateFriendConnectionSuccess() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		underTest.createFriendConnection(request);
	}

}
