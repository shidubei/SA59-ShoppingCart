package sg.nus.iss.shoppingCart.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.UpdateDetails;
import sg.nus.iss.shoppingCart.service.CustomerService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// validator used for sign up data
@Component
public class UpdateDetailsValidator implements Validator {

	
	//@Autowired
	//private CustomerRepository customerRepo;

	@Autowired
	private CustomerService customerService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateDetails.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		UpdateDetails updateDetails = (UpdateDetails) obj;
		
		// get the current account
		Optional<Customer> currentCustomerOptional = customerService.findById(updateDetails.getCustomerId());
		Customer currentCustomer = currentCustomerOptional.get();
		
		// check the username
		String username = updateDetails.getUsername();
		List<Customer> sameUserName = customerService.findByName(username);
		if (currentCustomer.getName().equals(username)) {
			// if username is not going to change this part is clear
		} else if (username.toLowerCase().equals("admin")) {
			errors.rejectValue("username", "error.noadmin","'Admin' and other variations is not a valid username.");
		} else if (sameUserName.size() >= 1) {
			errors.rejectValue("username","error.usedusername",
					"This username is already in use. Please use a different username.");
		}
		
		// check that the old password is correct
		if (updateDetails.getOldPassword().equals("")) {
			errors.rejectValue("oldPassword", "error.noPassword","Please key in your old password for validation purposes.");
		} else if (!currentCustomer.getPassword().equals(updateDetails.getOldPassword())) {
			errors.rejectValue("oldPassword", "error.wrongOldPassword","Your old password is wrong.");
		}
		
		// check if password is correct;
		String password1 = updateDetails.getPassword1();
		if (password1.equals("")) {
			// if we are not changing password can just leave this blank
		} else if (PasswordValidate.isValidPassword(password1) == false) {
			errors.rejectValue("password1","error.passwordisgoodpassword",
					"Password must have at least 8 characters and contain an uppercase letter, lowercase letter and digit");
		}
		
		// check if the second confirm password is correct
		String password2 = updateDetails.getPassword2();
		if (password2.equals(password1)==false) {
			errors.rejectValue("password2","error.password2match",
					"The two passwords must match");
		}
		
		
	}
}
