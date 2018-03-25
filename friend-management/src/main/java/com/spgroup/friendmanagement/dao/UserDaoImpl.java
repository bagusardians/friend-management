package com.spgroup.friendmanagement.dao;

import java.util.List;
import java.util.Objects;

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
		UserDto existingUser = repo.findByEmail(user.getEmail());
		if (Objects.isNull(existingUser)) {
			return repo.save(user);
		} else {
			return user;
		}
	}

	@Override
	public UserDto fetchUserByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public List<UserDto> fetchUsersByIds(List<String> ids) {
		return repo.findAllById(ids);
	}

}
