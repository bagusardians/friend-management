package com.spgroup.friendmanagement.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class UserDto {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	public UserDto() {

	}

	public UserDto(String email) {
		this.email = email;
	}
}
