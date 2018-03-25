package com.spgroup.friendmanagement.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.spgroup.friendmanagement.dao.UserDao;
import com.spgroup.friendmanagement.dao.UserRelationDao;
import com.spgroup.friendmanagement.dto.UserDto;
import com.spgroup.friendmanagement.entity.BasicResponseEntity;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsResponseEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;

@RunWith(MockitoJUnitRunner.class)
public class FriendManagementServiceImplTest {

	@InjectMocks
	FriendManagementServiceImpl underTest;

	@Mock
	UserDao userDao;

	@Mock
	UserRelationDao userRelationDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateFriendConnectionSuccess() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("ardi@yahoo.com");
		request.setFriends(friends);
		Mockito.doReturn(new UserDto("bagus@yahoo.com")).when(userDao).addUser(new UserDto("bagus@yahoo.com"));
		Mockito.doReturn(new UserDto("ardi@yahoo.com")).when(userDao).addUser(new UserDto("ardi@yahoo.com"));

		BasicResponseEntity expected = new BasicResponseEntity();
		expected.setSuccess(true);
		BasicResponseEntity actual = underTest.createFriendConnection(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionNullRequest() {
		underTest.createFriendConnection(null);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionLessThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionGreaterThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("ardi@yahoo.com");
		friends.add("syah@yahoo.com");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectioInvalidEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("sdfsdfsdf");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionSameEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("bagus@yahoo.com");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test
	public void testGetFriendListSuccess() {
		String email = "test@example.com";
		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		FriendsResponseEntity actual = underTest.getFriendList(email);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

}
