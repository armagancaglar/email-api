package com.armagancaglar.rest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class Utility {
	
	//Email validator dropping invalid emails and unwanted domains
	public static boolean emailValidator(String email) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9._-]+@+(comeon.com|cherry.se)");
		Matcher m = p.matcher(email);		
		/*if(EmailValidator.getInstance().isValid(email))
			int atIndex = email.indexOf("@") + 1;
			String domainValidation = email.substring(atIndex, email.length());
			if(domainValidation.equals("comeon.com") || domainValidation.equals("cherry.se")) {
				return true;
			}*/
		if(m.matches() && EmailValidator.getInstance().isValid(email)){
			return true;
		}
		return false;
	}
}
