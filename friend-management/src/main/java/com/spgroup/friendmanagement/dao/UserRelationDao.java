package com.spgroup.friendmanagement.dao;

import java.util.List;

import com.spgroup.friendmanagement.dto.UserRelationDto;

public interface UserRelationDao {

	/**
	 * Add user relation to database
	 * 
	 * @param userRelation
	 * @return
	 */
	public UserRelationDto addUserRelation(UserRelationDto userRelation);

	/**
	 * retrieve user relation list from database based on id
	 * 
	 * @param id
	 * @return
	 */
	List<UserRelationDto> fetchUserRelationList(String id);

	/**
	 * fetch user relation from database based on id and related id
	 * 
	 * @param id
	 * @param relatedId
	 * @return
	 */
	UserRelationDto fetchCorrelationBetweenTwoUser(String id, String relatedId);

	/**
	 * fetch user relation list based on related id
	 * 
	 * @param relatedId
	 * @return
	 */
	List<UserRelationDto> fetchUserRelationListByRelatedId(String relatedId);
}
