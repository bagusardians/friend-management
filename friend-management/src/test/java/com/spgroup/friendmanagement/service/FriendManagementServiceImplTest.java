package com.spgroup.friendmanagement.service;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.spgroup.friendmanagement.dao.UserDao;
import com.spgroup.friendmanagement.dao.UserDaoImpl;
import com.spgroup.friendmanagement.dao.UserRelationDao;
import com.spgroup.friendmanagement.dao.UserRelationDaoImpl;
import com.spgroup.friendmanagement.dao.repository.UserRepository;
import com.spgroup.friendmanagement.dto.UserDto;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.ConnectionResponseEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;

@RunWith(MockitoJUnitRunner.class)
public class FriendManagementServiceImplTest {

	FriendManagementServiceImpl underTest = new FriendManagementServiceImpl();
	
	@InjectMocks
	UserDaoImpl userDao;
	
	@InjectMocks
	UserRelationDaoImpl userRelationDao;
	
	@Mock
	UserRepository userRepo;

	@Before
	public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
	}

	@Test
	@Ignore
	public void testCreateFriendConnectionSuccess() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("ardi@yahoo.com");
		request.setFriends(friends);
		Mockito.when(userRepo.save(new UserDto("ardi@yahoo.com"))).thenReturn(new UserDto("ardi@yahoo.com"));
		Mockito.when(userDao.addUser(new UserDto("bagus@yahoo.com"))).thenReturn(new UserDto("bagus@yahoo.com"));
		Mockito.when(userDao.addUser(new UserDto("ardi@yahoo.com"))).thenReturn(new UserDto("ardi@yahoo.com"));
		
		ConnectionResponseEntity expected = new ConnectionResponseEntity();
		expected.setSuccess(true);
		ConnectionResponseEntity actual = underTest.createFriendConnection(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test(expected= FriendServiceException.class)
	public void testCreateFriendConnectionNullRequest() {
		underTest.createFriendConnection(null);
	}

	@Test(expected= FriendServiceException.class)
	public void testCreateFriendConnectionLessThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected= FriendServiceException.class)
	public void testCreateFriendConnectionGreaterThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("ardi@yahoo.com");
		friends.add("syah@yahoo.com");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected= FriendServiceException.class)
	public void testCreateFriendConnectioInvalidEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("sdfsdfsdf");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected= FriendServiceException.class)
	public void testCreateFriendConnectionSameEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add("bagus@yahoo.com");
		friends.add("bagus@yahoo.com");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}



}
