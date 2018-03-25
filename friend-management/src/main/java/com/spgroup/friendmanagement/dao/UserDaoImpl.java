package com.spgroup.friendmanagement.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.dao.repository.UserRepository;
import com.spgroup.friendmanagement.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	UserRepository repo;

	@Override
	public UserDto addUser(UserDto user) {
		log.info("trying user to db");
		UserDto existingUser = repo.findByEmail(user.getEmail());
		if (Objects.isNull(existingUser)) {
			log.info("adding user to db");
			return repo.save(user);
		} else {
			log.info("user exist on db");
			return existingUser;
		}
	}

	@Override
	public UserDto fetchUserByEmail(String email) {
		log.info("find user by email");
		return repo.findByEmail(email);
	}

	@Override
	public List<UserDto> fetchUsersByIds(List<String> ids) {
		log.info("find user by ids");
		return repo.findAllById(ids);
	}

	@Override
	public List<UserDto> fetchUserByEmail(List<String> emails) {
		log.info("find user by emails");
		return repo.findByEmailIn(emails);
	}

}
