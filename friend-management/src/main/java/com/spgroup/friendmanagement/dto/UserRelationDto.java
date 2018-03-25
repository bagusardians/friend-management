package com.spgroup.friendmanagement.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.spgroup.friendmanagement.enumeration.RelationTypeEnum;

import lombok.Data;

@Entity
@Table(name = "user_relation")
@Data
public class UserRelationDto {

	@EmbeddedId
	private UserRelationKey key;

	@Column(name = "relation_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private RelationTypeEnum relationType;

	@Column(name = "is_block")
	private boolean block;

	public UserRelationDto() {

	}

	public UserRelationDto(UserRelationKey key, RelationTypeEnum relationType, boolean block) {
		this.key = key;
		this.relationType = relationType;
		this.block = block;
	}

}
