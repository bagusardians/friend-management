package com.spgroup.friendmanagement.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_relation")
public class UserRelationDto {

	@EmbeddedId
	private UserRelationKey key;

	@Column(name="relation_type", nullable = false)
	private String relationType;

	@Column(name="is_block")
	private boolean block;
	

	public UserRelationDto(UserRelationKey key, String relationType, boolean block) {
		this.key = key;
		this.relationType = relationType;
		this.block = block;
	}

	public UserRelationKey getKey() {
		return key;
	}

	public void setKey(UserRelationKey key) {
		this.key = key;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}
	
	
}
