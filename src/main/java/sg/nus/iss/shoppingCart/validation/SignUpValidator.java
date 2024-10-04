package sg.nus.iss.shoppingCart.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.shoppingCart.model.SignUp;

import org.springframework.stereotype.Component;


@Component
public class SignUpValidator implements Validator {

	public int min_length = 8;
	public boolean need_upper = true;
	public boolean need_lower = true;
	public boolean need_digit = true;
	public boolean need_special = false;
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SignUp.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		SignUp signUp = (SignUp) obj;
		// check if password is correct;
		String password1 = signUp.getPassword1();
		if (this.isValidPassword(password1) == false) {
			errors.rejectValue("password1","error.password1",
					"Password must have at least 8 characters and contain an uppercase letter, lowercase letter and digit");
		}
		String password2 = signUp.getPassword2();
		if (password2.equals(password1)==false) {
			errors.rejectValue("password2","error.password2",
					"The two passwords must match");
		}
	}
	
	public boolean isValidPassword(String username) {
		
		if (username.length() < min_length) {
			return false;
		}
		
		int have_upper = 0;
		int have_lower = 0;
		int have_digit = 0;
		int have_special = 0;
		
		int i = 0;
		char char_i;
		
		for (i=0;i<username.length();i++) {
			char_i = username.charAt(i);
				
			if (Character.isLetter(char_i) & Character.isUpperCase(char_i)) {
				// is upper case
				have_upper += 1;
			} else if (Character.isLetter(char_i) & Character.isLowerCase(char_i)) {
				// is lower case
				have_lower += 1;
			} else if (Character.isDigit(char_i)) {
				// is a digit
				have_digit += 1;
			} else {
				// others
				have_special += 1;
			}
		}
		
		if (need_upper & have_upper == 0) {
			return false;
		} if (need_lower & have_lower == 0) {
			return false;
		} if (need_digit & have_digit == 0) {
			return false;
		} if (need_special & have_special == 0) {
			return false;
		}
		
		return true;
	}
}
