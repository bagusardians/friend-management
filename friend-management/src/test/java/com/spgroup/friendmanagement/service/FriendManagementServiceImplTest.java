package com.spgroup.friendmanagement.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import com.spgroup.friendmanagement.dto.UserRelationDto;
import com.spgroup.friendmanagement.dto.UserRelationKey;
import com.spgroup.friendmanagement.entity.BasicResponseEntity;
import com.spgroup.friendmanagement.entity.ConnectionRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsRequestEntity;
import com.spgroup.friendmanagement.entity.FriendsResponseEntity;
import com.spgroup.friendmanagement.entity.SubscribeRequestEntity;
import com.spgroup.friendmanagement.exception.FriendServiceException;

@RunWith(MockitoJUnitRunner.class)
public class FriendManagementServiceImplTest {

	@InjectMocks
	FriendManagementServiceImpl underTest;

	@Mock
	UserDao userDao;

	@Mock
	UserRelationDao userRelationDao;

	private final String MOCK_UUID_1 = UUID.randomUUID().toString();

	private final String MOCK_UUID_2 = UUID.randomUUID().toString();

	private final String MOCK_UUID_3 = UUID.randomUUID().toString();

	private final String MOCK_EMAIL_1 = "bagus@yahoo.com";

	private final String MOCK_EMAIL_2 = "ardi@yahoo.com";

	private final String MOCK_EMAIL_3 = "syah@yahoo.com";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateFriendConnectionSuccess() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		request.setFriends(friends);
		Mockito.doReturn(new UserDto(MOCK_EMAIL_1)).when(userDao).addUser(new UserDto(MOCK_EMAIL_1));
		Mockito.doReturn(new UserDto(MOCK_EMAIL_2)).when(userDao).addUser(new UserDto(MOCK_EMAIL_2));

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
	public void testCreateFriendConnectionEmptyEmails() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionLessThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionGreaterThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		friends.add("syah@yahoo.com");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionInvalidEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add("sdfsdfsdf");
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateFriendConnectionSameEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_1);
		request.setFriends(friends);
		underTest.createFriendConnection(request);
	}

	@Test
	public void testGetFriendListSuccess() {
		FriendsRequestEntity request = new FriendsRequestEntity();
		request.setEmail(MOCK_EMAIL_1);
		List<UserRelationDto> relationList = new ArrayList<>();
		UserRelationKey key = new UserRelationKey(MOCK_UUID_1, MOCK_UUID_2);
		relationList.add(new UserRelationDto(key, "FRIEND", false));
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(relationList).when(userRelationDao).fetchUserRelationList(MOCK_UUID_1);

		List<String> userIdList = new ArrayList<>();
		userIdList.add(MOCK_UUID_2);
		List<UserDto> userDtoList = new ArrayList<>();
		userDtoList.add(new UserDto(MOCK_UUID_2, MOCK_EMAIL_2));

		Mockito.doReturn(userDtoList).when(userDao).fetchUsersByIds(userIdList);
		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		FriendsResponseEntity actual = underTest.getFriendList(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test
	public void testGetFriendListNoFriendUser() {
		FriendsRequestEntity request = new FriendsRequestEntity();
		request.setEmail(MOCK_EMAIL_1);
		List<UserRelationDto> relationList = new ArrayList<>();
		UserRelationKey key = new UserRelationKey(MOCK_UUID_1, MOCK_UUID_2);
		relationList.add(new UserRelationDto(key, "FRIEND", false));
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(relationList).when(userRelationDao).fetchUserRelationList(MOCK_UUID_1);

		List<String> userIdList = new ArrayList<>();
		userIdList.add(MOCK_UUID_2);

		Mockito.doReturn(null).when(userDao).fetchUsersByIds(userIdList);
		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		FriendsResponseEntity actual = underTest.getFriendList(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test
	public void testGetFriendListNoRelation() {
		FriendsRequestEntity request = new FriendsRequestEntity();
		request.setEmail(MOCK_EMAIL_1);
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(null).when(userRelationDao).fetchUserRelationList(MOCK_UUID_1);
		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		FriendsResponseEntity actual = underTest.getFriendList(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test(expected = FriendServiceException.class)
	public void testGetFriendListNoRequestUser() {
		FriendsRequestEntity request = new FriendsRequestEntity();
		request.setEmail(MOCK_EMAIL_1);
		List<UserRelationDto> relationList = new ArrayList<>();
		UserRelationKey key = new UserRelationKey(MOCK_UUID_1, MOCK_UUID_2);
		relationList.add(new UserRelationDto(key, "FRIEND", false));
		Mockito.doReturn(null).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		underTest.getFriendList(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetFriendListInvalidEmail() {
		String email = "invalid";
		FriendsRequestEntity request = new FriendsRequestEntity();
		request.setEmail(email);
		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		FriendsResponseEntity actual = underTest.getFriendList(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test(expected = FriendServiceException.class)
	public void testGetFriendListInvalidRequest() {
		FriendsRequestEntity request = null;
		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		FriendsResponseEntity actual = underTest.getFriendList(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test
	public void testGetCommonFriendListSuccess() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		request.setFriends(friends);
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(new UserDto(MOCK_UUID_2, MOCK_EMAIL_2)).when(userDao).fetchUserByEmail(MOCK_EMAIL_2);
		List<UserRelationDto> relationList = new ArrayList<>();
		UserRelationKey key1 = new UserRelationKey(MOCK_UUID_1, MOCK_UUID_3);
		relationList.add(new UserRelationDto(key1, "FRIEND", false));
		Mockito.doReturn(relationList).when(userRelationDao).fetchUserRelationList(MOCK_UUID_1);
		List<UserRelationDto> relationList2 = new ArrayList<>();
		UserRelationKey key2 = new UserRelationKey(MOCK_UUID_2, MOCK_UUID_3);
		relationList2.add(new UserRelationDto(key2, "FRIEND", false));
		Mockito.doReturn(relationList2).when(userRelationDao).fetchUserRelationList(MOCK_UUID_2);

		List<String> userIdList = new ArrayList<>();
		userIdList.add(MOCK_UUID_3);
		List<UserDto> userDtoList = new ArrayList<>();
		userDtoList.add(new UserDto(MOCK_UUID_3, MOCK_EMAIL_3));
		Mockito.doReturn(userDtoList).when(userDao).fetchUsersByIds(userIdList);

		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		expected.setFriends(Arrays.asList(MOCK_EMAIL_3));
		expected.setCount(1);
		FriendsResponseEntity actual = underTest.getCommonFriendList(request);
		assertEquals(expected, actual);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListInvalidFirst() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		request.setFriends(friends);
		Mockito.doReturn(null).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		underTest.getCommonFriendList(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListInvalidSecond() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		request.setFriends(friends);
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(null).when(userDao).fetchUserByEmail(MOCK_EMAIL_2);
		underTest.getCommonFriendList(request);
	}

	@Test
	public void testGetCommonFriendListEmptyRelation() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		request.setFriends(friends);
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(new UserDto(MOCK_UUID_2, MOCK_EMAIL_2)).when(userDao).fetchUserByEmail(MOCK_EMAIL_2);
		Mockito.doReturn(null).when(userRelationDao).fetchUserRelationList(MOCK_UUID_1);
		List<UserRelationDto> relationList2 = new ArrayList<>();
		UserRelationKey key2 = new UserRelationKey(MOCK_UUID_2, MOCK_UUID_3);
		relationList2.add(new UserRelationDto(key2, "FRIEND", false));
		Mockito.doReturn(relationList2).when(userRelationDao).fetchUserRelationList(MOCK_UUID_2);

		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		expected.setFriends(new ArrayList<>());
		expected.setCount(0);
		FriendsResponseEntity actual = underTest.getCommonFriendList(request);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetCommonFriendListEmptyRelationSecond() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		request.setFriends(friends);
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(new UserDto(MOCK_UUID_2, MOCK_EMAIL_2)).when(userDao).fetchUserByEmail(MOCK_EMAIL_2);
		List<UserRelationDto> relationList1 = new ArrayList<>();
		UserRelationKey key1 = new UserRelationKey(MOCK_UUID_1, MOCK_UUID_3);
		relationList1.add(new UserRelationDto(key1, "FRIEND", false));
		Mockito.doReturn(relationList1).when(userRelationDao).fetchUserRelationList(MOCK_UUID_1);
		Mockito.doReturn(null).when(userRelationDao).fetchUserRelationList(MOCK_UUID_2);

		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		expected.setFriends(new ArrayList<>());
		expected.setCount(0);
		FriendsResponseEntity actual = underTest.getCommonFriendList(request);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetCommonFriendListEmptyCommonFriends() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		request.setFriends(friends);
		Mockito.doReturn(new UserDto(MOCK_UUID_1, MOCK_EMAIL_1)).when(userDao).fetchUserByEmail(MOCK_EMAIL_1);
		Mockito.doReturn(new UserDto(MOCK_UUID_2, MOCK_EMAIL_2)).when(userDao).fetchUserByEmail(MOCK_EMAIL_2);
		List<UserRelationDto> relationList = new ArrayList<>();
		UserRelationKey key1 = new UserRelationKey(MOCK_UUID_1, MOCK_UUID_3);
		relationList.add(new UserRelationDto(key1, "FRIEND", false));
		Mockito.doReturn(relationList).when(userRelationDao).fetchUserRelationList(MOCK_UUID_1);
		List<UserRelationDto> relationList2 = new ArrayList<>();
		UserRelationKey key2 = new UserRelationKey(MOCK_UUID_2, MOCK_UUID_3);
		relationList2.add(new UserRelationDto(key2, "FRIEND", false));
		Mockito.doReturn(relationList2).when(userRelationDao).fetchUserRelationList(MOCK_UUID_2);

		List<String> userIdList = new ArrayList<>();
		userIdList.add(MOCK_UUID_3);
		List<UserDto> userDtoList = new ArrayList<>();
		Mockito.doReturn(userDtoList).when(userDao).fetchUsersByIds(userIdList);

		FriendsResponseEntity expected = new FriendsResponseEntity();
		expected.setSuccess(true);
		expected.setFriends(new ArrayList<>());
		expected.setCount(0);
		FriendsResponseEntity actual = underTest.getCommonFriendList(request);
		assertEquals(expected, actual);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListNullRequest() {
		underTest.createFriendConnection(null);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListEmptyEmails() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		request.setFriends(friends);
		underTest.getCommonFriendList(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListLessThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		request.setFriends(friends);
		underTest.getCommonFriendList(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListGreaterThanTwo() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_2);
		friends.add("syah@yahoo.com");
		request.setFriends(friends);
		underTest.getCommonFriendList(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListInvalidEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add("sdfsdfsdf");
		request.setFriends(friends);
		underTest.getCommonFriendList(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testGetCommonFriendListSameEmail() {
		ConnectionRequestEntity request = new ConnectionRequestEntity();
		List<String> friends = new ArrayList<>();
		friends.add(MOCK_EMAIL_1);
		friends.add(MOCK_EMAIL_1);
		request.setFriends(friends);
		underTest.getCommonFriendList(request);
	}

	@Test
	public void testCreateSubscribeConnectionSuccess() {
		SubscribeRequestEntity request = new SubscribeRequestEntity();
		request.setRequestor(MOCK_EMAIL_1);
		request.setTarget(MOCK_EMAIL_2);
		Mockito.doReturn(new UserDto(MOCK_EMAIL_1)).when(userDao).addUser(new UserDto(MOCK_EMAIL_1));
		Mockito.doReturn(new UserDto(MOCK_EMAIL_2)).when(userDao).addUser(new UserDto(MOCK_EMAIL_2));
		BasicResponseEntity expected = new BasicResponseEntity();
		expected.setSuccess(true);
		BasicResponseEntity actual = underTest.createSubscribeConnection(request);
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateSubscribeConnectionNull() {
		underTest.createSubscribeConnection(null);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateSubscribeConnectionEmptyRequestor() {
		SubscribeRequestEntity request = new SubscribeRequestEntity();
		request.setRequestor("");
		request.setTarget(MOCK_EMAIL_2);
		underTest.createSubscribeConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateSubscribeConnectionEmptyTarget() {
		SubscribeRequestEntity request = new SubscribeRequestEntity();
		request.setRequestor(MOCK_EMAIL_1);
		request.setTarget("");
		underTest.createSubscribeConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateSubscribeConnectionItself() {
		SubscribeRequestEntity request = new SubscribeRequestEntity();
		request.setRequestor(MOCK_EMAIL_1);
		request.setTarget(MOCK_EMAIL_1);
		underTest.createSubscribeConnection(request);
	}

	@Test(expected = FriendServiceException.class)
	public void testCreateSubscribeConnection() {
		SubscribeRequestEntity request = new SubscribeRequestEntity();
		request.setRequestor("invalid");
		request.setTarget("invalid2");
		underTest.createSubscribeConnection(request);
	}

}
