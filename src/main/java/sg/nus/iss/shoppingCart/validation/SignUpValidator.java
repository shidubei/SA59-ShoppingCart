package sg.nus.iss.shoppingCart.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.SignUp;
import sg.nus.iss.shoppingCart.service.CustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// validator used for sign up data
@Component
public class SignUpValidator implements Validator {

	
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
		if (PasswordValidate.isValidPassword(password1) == false) {
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
		if (username.toLowerCase().equals("admin")) {
			errors.rejectValue("username", "error.noadmin","'Admin' and other variations is not a valid username.");
		} else if (sameUserName.size() > 0) {
			errors.rejectValue("username","error.usedusername",
					"This username is already in use. Please use a different username.");
		}
		
	}
	

}
