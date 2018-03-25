package com.spgroup.friendmanagement.util;

import org.springframework.util.StringUtils;

public class EmailValidationUtil {

	  private EmailValidationUtil() {
	    throw new IllegalStateException("Utility class");
	  }
	
	private static final String EMAIL_PATTERN = "^[a-z0-9._-]+@[a-z0-9-]+(?:\\.[a-z0-9-]+)*$";
	
	public static boolean isEmailsValid(String... emails) {
		for (String email : emails) {
			if(StringUtils.isEmpty(email)) {
				return false;
			}
			if(!email.matches(EMAIL_PATTERN)) {
				return false;
			}
		}
		return true;
	}
}
