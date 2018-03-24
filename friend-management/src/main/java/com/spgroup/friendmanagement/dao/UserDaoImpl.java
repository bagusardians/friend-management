package com.spgroup.friendmanagement.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.dao.repository.UserRepository;
import com.spgroup.friendmanagement.dto.UserDto;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	UserRepository repo;
	
	@Override
	public UserDto addUser(UserDto user) {
		return repo.save(user);
	}

}
