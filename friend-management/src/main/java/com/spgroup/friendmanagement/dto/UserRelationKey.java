package com.spgroup.friendmanagement.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserRelationKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1447691574693959344L;

	@Column(name="id", nullable = false)
	private UUID id;

	@Column(name="related_id", nullable = false)
	private UUID relatedId;
	
	public UserRelationKey(UUID id, UUID relatedId) {
		this.id = id;
		this.relatedId = relatedId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(UUID relatedId) {
		this.relatedId = relatedId;
	}
	
	
}
