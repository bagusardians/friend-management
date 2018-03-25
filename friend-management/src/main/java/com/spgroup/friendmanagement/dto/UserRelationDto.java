package com.spgroup.friendmanagement.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_relation")
@Data
public class UserRelationDto {

	@EmbeddedId
	private UserRelationKey key;

	@Column(name = "relation_type", nullable = false)
	private String relationType;

	@Column(name = "is_block")
	private boolean block;

	public UserRelationDto() {

	}

	public UserRelationDto(UserRelationKey key, String relationType, boolean block) {
		this.key = key;
		this.relationType = relationType;
		this.block = block;
	}

}
