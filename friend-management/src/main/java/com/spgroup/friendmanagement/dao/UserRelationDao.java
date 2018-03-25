package com.spgroup.friendmanagement.dao;

import java.util.List;

import com.spgroup.friendmanagement.dto.UserRelationDto;

public interface UserRelationDao {

	public UserRelationDto addUserRelation(UserRelationDto userRelation);

	List<UserRelationDto> fetchUserRelationList(String email);
}
