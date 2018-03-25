package com.spgroup.friendmanagement.dao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spgroup.friendmanagement.dto.UserDto;

@Repository
@Component
public interface UserRepository extends CrudRepository<UserDto, String> {

	UserDto findByEmail(String email);

	List<UserDto> findAllById(Iterable<String> ids);

}