package com.spgroup.friendmanagement.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.dto.UserDto;

@Service
public interface UserDao {

	/**
	 * add user to database
	 * 
	 * @param user
	 * @return
	 */
	public UserDto addUser(UserDto user);

	/**
	 * fetch user from database by email
	 * 
	 * @param email
	 * @return
	 */
	UserDto fetchUserByEmail(String email);

	/**
	 * fetch user list by list of id
	 * 
	 * @param ids
	 * @return
	 */
	List<UserDto> fetchUsersByIds(List<String> ids);

}
