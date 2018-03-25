package com.spgroup.friendmanagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.dao.repository.UserRelationRepository;
import com.spgroup.friendmanagement.dto.UserRelationDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserRelationDaoImpl implements UserRelationDao {

	@Autowired
	UserRelationRepository repo;

	@Override
	public UserRelationDto addUserRelation(UserRelationDto userRelation) {
		log.info("save relation");
		return repo.save(userRelation);
	}

	@Override
	public List<UserRelationDto> fetchUserRelationList(String id) {
		log.info("fetch by id");
		return repo.findByKeyId(id);
	}

	@Override
	public List<UserRelationDto> fetchUserRelationListByRelatedId(String relatedId) {
		log.info("fetch by related id");
		return repo.findByKeyRelatedId(relatedId);
	}

	@Override
	public UserRelationDto fetchCorrelationBetweenTwoUser(String id, String relatedId) {
		log.info("fetch by id and related id");
		return repo.findByKeyIdAndKeyRelatedId(id, relatedId);
	}

}
