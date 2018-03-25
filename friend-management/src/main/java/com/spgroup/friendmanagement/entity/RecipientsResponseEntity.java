package com.spgroup.friendmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecipientsResponseEntity extends BasicResponseEntity {

	@ApiModelProperty(value = "list of recipients email address")
	private List<String> recipients;

	public static RecipientsResponseEntity createEmptyRecipientList() {
		RecipientsResponseEntity response = new RecipientsResponseEntity();
		response.setSuccess(true);
		response.setRecipients(new ArrayList<>());
		return response;
	}
}
