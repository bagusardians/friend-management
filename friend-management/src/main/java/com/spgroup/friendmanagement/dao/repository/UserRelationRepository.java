package com.spgroup.friendmanagement.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spgroup.friendmanagement.dto.UserRelationDto;
import com.spgroup.friendmanagement.dto.UserRelationKey;

@Repository
public interface UserRelationRepository extends CrudRepository<UserRelationDto,UserRelationKey> {
     
 
}