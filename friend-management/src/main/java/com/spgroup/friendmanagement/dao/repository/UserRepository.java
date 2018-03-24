package com.spgroup.friendmanagement.dao.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spgroup.friendmanagement.dto.UserDto;

@Repository
public interface UserRepository extends CrudRepository<UserDto,UUID> {
     
 
}