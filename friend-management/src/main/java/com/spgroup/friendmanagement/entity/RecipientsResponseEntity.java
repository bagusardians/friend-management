package com.spgroup.friendmanagement.entity;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecipientsResponseEntity extends BasicResponseEntity {

	private List<String> recipients;
}
