package com.spgroup.friendmanagement.dao;

import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.dto.UserDto;

@Service
public interface UserDao {

	public UserDto addUser(UserDto user);
	
}
