package com.spgroup.friendmanagement.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class EmailUtil {

	private EmailUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String EMAIL_PATTERN = "^[a-z0-9._-]+@[a-z0-9-]+(?:\\.[a-z0-9-]+)*$";

	public static boolean isEmailsValid(String... emails) {
		for (String email : emails) {
			if (StringUtils.isEmpty(email)) {
				return false;
			}
			if (!email.matches(EMAIL_PATTERN)) {
				return false;
			}
		}
		return true;
	}

	public static List<String> extractEmailFromText(String text) {
		if (StringUtils.isEmpty(text)) {
			return new ArrayList<>();
		}
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
		List<String> emailList = new ArrayList<>();
		while (m.find()) {
			emailList.add(m.group());
		}
		return emailList;
	}
}
