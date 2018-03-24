package com.spgroup.friendmanagement.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spgroup.friendmanagement.dao.repository.UserRelationRepository;
import com.spgroup.friendmanagement.dto.UserRelationDto;

@Service
public class UserRelationDaoImpl implements UserRelationDao{

	@Autowired
	UserRelationRepository repo;
	
	@Override
	public UserRelationDto addUserRelation(UserRelationDto userRelation) {
		return repo.save(userRelation);
	}

}
