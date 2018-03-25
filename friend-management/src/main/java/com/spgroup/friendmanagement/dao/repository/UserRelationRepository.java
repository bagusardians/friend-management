package com.spgroup.friendmanagement.dao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spgroup.friendmanagement.dto.UserRelationDto;
import com.spgroup.friendmanagement.dto.UserRelationKey;

@Repository
public interface UserRelationRepository extends CrudRepository<UserRelationDto, UserRelationKey> {

	public List<UserRelationDto> findByKeyId(String id);

	public List<UserRelationDto> findByKeyRelatedId(String relatedId);

	public UserRelationDto findByKeyIdAndKeyRelatedId(String id, String relatedId);

}