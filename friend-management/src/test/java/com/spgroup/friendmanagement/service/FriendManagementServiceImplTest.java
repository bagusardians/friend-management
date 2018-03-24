package com.spgroup.friendmanagement.service;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.ConnectionResponseEntity;

public class FriendManagementServiceImplTest {

	FriendManagementServiceImpl underTest = new FriendManagementServiceImpl();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateFriendConnectionSuccess() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("ardi@yahoo.com");
		request.setFriends(friends);
		
		ConnectionResponseEntity expected = new ConnectionResponseEntity();
		expected.setSuccess(true);
		ConnectionResponseEntity actual = underTest.createFriendConnection(request);
		assertEquals(expected, actual);
	}

}
