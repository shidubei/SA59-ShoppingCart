package sg.nus.iss.shoppingCart.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

// This is for testing the interface change between login and logout

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;


import sg.nus.iss.shoppingCart.validation.SignUpValidator;
import sg.nus.iss.shoppingCart.validation.UpdateDetailsValidator;
import sg.nus.iss.shoppingCart.model.SignUp;
import sg.nus.iss.shoppingCart.model.UpdateDetails;
import sg.nus.iss.shoppingCart.interfacemethods.CustomerInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.service.CustomerService;
import sg.nus.iss.shoppingCart.service.ShoppingCartService;

@Controller
public class UpdateAccountController {

	// UpdateDetailsValidator is for validating the following on the sign-up sheet:
	// 1. Username is unique (does not already exist in the Customer database)
	// 2. Old password is correct
	// 2. New password is at least 8 characters
	// 3. New password contains at least 1 uppercase, 1 lowercase and 1 numeric
	// 4. New password 1 and New password 2 are the same
	@Autowired
	private UpdateDetailsValidator updateDetailsValidator;
	
	// Access to the customer service
	@Autowired
	private CustomerInterfacemethods customerService;
	

	@Autowired
	private void setCustomerService(CustomerService customerService, ShoppingCartService scService) {
		this.customerService=customerService;
	}
	
	@InitBinder
	private void initSignUpBinder(WebDataBinder binder) {
		// Other binding
		binder.addValidators(updateDetailsValidator);
	}
	
	// updatedetails is the page for updating current account
	@GetMapping("/updatedetails")
	public String updateDetailsPage(Model model, HttpSession sessionObj) {
		// If the 'isLoggedIn' session object is not made assume not logged in
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			sessionObj.setAttribute("isLoggedIn",false);
		}
		// NOTE: the interceptors should redirect you back to the login screen
		// if you are not logged in
		
		// get the current account
		int customerId = (int) sessionObj.getAttribute("customerId");
		Optional<Customer> currentCustomerOptional = customerService.findById(customerId);
		Customer currentCustomer = currentCustomerOptional.get();
		
		// create the update details data holder
		UpdateDetails updateDetails = new UpdateDetails();
		updateDetails.setCustomerId(customerId);
		updateDetails.setUsername(currentCustomer.getName());
		updateDetails.setEmail(currentCustomer.getEmail());
		updateDetails.setContactNumber(currentCustomer.getContactNumber());
		model.addAttribute("updatedetails",updateDetails);
		return "update-account";
	}
	
	@PostMapping("/updatedetails")
	public String postUpdateDetails(@Valid @ModelAttribute UpdateDetails updateDetails,
										BindingResult bindingResult,
										Model model, HttpSession sessionObj) {
		// Print update details form details for personal validation
		System.out.println("Id: "+updateDetails.getCustomerId());
		System.out.println("Email: "+updateDetails.getEmail());
		System.out.println("Username: "+updateDetails.getUsername());
		System.out.println("ContactNumber: "+updateDetails.getContactNumber());
		System.out.println("OldPassword: "+updateDetails.getOldPassword());
		System.out.println("Password1: "+updateDetails.getPassword1());
		System.out.println("Password2: "+updateDetails.getPassword2());
		// If there are errors (ex. empty email, password not strong enough)
		// redirect back to sign up page
		if (bindingResult.hasErrors()) {
			System.out.println("Errors were found:");
			System.out.println("Errors found: " + bindingResult.getErrorCount());
			model.addAttribute("updatedetails",updateDetails); // keep the form data
			// Question: In workshops re-adding bindingResult was unnecessary. However here it is necessary.
			model.addAttribute("org.springframework.validation.BindingResult.updatedetails", bindingResult); // Add the binding result
			return "update-account";
		}
		customerService.updateCustomer(updateDetails);
		sessionObj.setAttribute("customerName", updateDetails.getUsername());
		// get the current account
		//int customerId = (int) sessionObj.getAttribute("customerId");
		//Optional<Customer> currentCustomerOptional = customerService.findById(customerId);
		//Customer currentCustomer = currentCustomerOptional.get();
		//currentCustomer.setEmail(updateDetails.getEmail());
		//currentCustomer.setName(updateDetails.getUsername());
		//currentCustomer.setContactNumber(updateDetails.getContactNumber());
		//if (!updateDetails.getPassword1().equals("")) {
		//	currentCustomer.setPassword(updateDetails.getPassword1());
		//}
		return "logstat";
		
	}
}
