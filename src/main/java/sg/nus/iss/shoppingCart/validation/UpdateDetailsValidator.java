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

/**
 * Creator: Mark
 * Explain: Valid when set UpdeteDetail data
 */
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
		System.out.println("customerId"+updateDetails.getCustomerId());
		Customer currentCustomer = currentCustomerOptional.get();
		
		// check the username
		String username = updateDetails.getName();
		List<Customer> sameUserName = customerService.findByName(username);
		if (currentCustomer.getName().equals(username)) {
			// if username is not going to change this part is clear
		} else if (sameUserName.size() >= 1) {
			errors.rejectValue("name","error.usedusername",
					"This username is already in use. Please use a different username.");
		}
	
		
	}
}
