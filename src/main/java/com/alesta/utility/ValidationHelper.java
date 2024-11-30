package com.alesta.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ValidationHelper {
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,15}$");
	
	public static boolean isValid(String text) {
		return text != null && !text.isEmpty();
	}
	
	public static boolean isValid(long number) {
		return number > 0;
	}
	
	public static boolean isValid(LocalDate date) {
		return date != null;
	}
	
	public static boolean isValidEmail(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}
	
	public static boolean isValidTelephoneNo(String telephoneNo) {
		return PHONE_PATTERN.matcher(telephoneNo).matches();
	}

}
