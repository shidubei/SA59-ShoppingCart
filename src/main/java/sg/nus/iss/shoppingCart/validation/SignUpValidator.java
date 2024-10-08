package sg.nus.iss.shoppingCart.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.SignUp;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;
import sg.nus.iss.shoppingCart.service.CustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// validator used for sign up data
@Component
public class SignUpValidator implements Validator {

	public int min_length = 8;
	public boolean need_upper = true;
	public boolean need_lower = true;
	public boolean need_digit = true;
	public boolean need_special = false;

	
	//@Autowired
	//private CustomerRepository customerRepo;

	@Autowired
	private CustomerService customerService;
	
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
			errors.rejectValue("password1","error.passwordisgoodpassword",
					"Password must have at least 8 characters and contain an uppercase letter, lowercase letter and digit");
		}
		// check if the confirm password is correct
		String password2 = signUp.getPassword2();
		if (password2.equals(password1)==false) {
			errors.rejectValue("password2","error.password2match",
					"The two passwords must match");
		}
		
		// check that the username is unique
		String username = signUp.getUsername();
		List<Customer> sameUserName = customerService.findByName(username);
		if (sameUserName.size() > 0) {
			errors.rejectValue("username","error.usedusername",
					"This username is already in use. Please use a different username.");
		}
		
	}
	
	// method used for checking if a password is valid
	public boolean isValidPassword(String password) {
		
		if (password.length() < min_length) {
			return false;
		}
		
		int have_upper = 0;
		int have_lower = 0;
		int have_digit = 0;
		int have_special = 0;
		
		int i = 0;
		char char_i;
		
		for (i=0;i<password.length();i++) {
			char_i = password.charAt(i);
				
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
