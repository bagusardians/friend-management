package com.spgroup.friendmanagement.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserRelationKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1447691574693959344L;

	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "related_id", nullable = false)
	private String relatedId;

	public UserRelationKey() {

	}

	public UserRelationKey(String id, String relatedId) {
		this.id = id;
		this.relatedId = relatedId;
	}

}
