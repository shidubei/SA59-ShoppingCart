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
import sg.nus.iss.shoppingCart.model.SignUp;
import sg.nus.iss.shoppingCart.interfacemethods.CustomerInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.service.CustomerService;

@Controller
public class AccountLoginController {

	// signUpValidator is for validating the following on the sign-up sheet:
	// 1. Username is unique (does not already exist in the Customer database)
	// 2. Password is at least 8 characters
	// 3. Password contains at least 1 uppercase, 1 lowercase and 1 numeric
	// 4. Password 1 and password 2 are the same
	@Autowired
	private SignUpValidator signUpValidator;
	
	// Access to the customer service
	@Autowired
	private CustomerInterfacemethods customerService;
	
	@Autowired
	private void setCustomerService(CustomerService customerService) {
		this.customerService=customerService;
	}
	
	@InitBinder
	private void initSignUpBinder(WebDataBinder binder) {
		// Other binding
		binder.addValidators(signUpValidator);
	}
	
	// Go to login screen
	@GetMapping("/login")
	public String loginPage(Model model,HttpSession sessionObj) {
		// If the 'isLoggedIn' session object is not made assume not logged in
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			sessionObj.setAttribute("isLoggedIn",false);
		}
		// if you are already logged in redirect to the main page
		//if ((boolean) sessionObj.getAttribute("isLoggedIn") == true) {
		//	return "redirect:/logstat";
		//}
		// These model attributes are to save what is typed on the login screen
		model.addAttribute("username","");
		model.addAttribute("password","");
		// showWrongPasswordError will be set to true if the username/ password
		// the user keyed in is wrong. It will cause the 'invalid password' message
		// to appear.
		model.addAttribute("showWrongPasswordError",false);
		// uses 'login.html'
		return "login";
	}
	
	// postmapping on login
	@PostMapping("/login")
	public String loginToAccount(Model model,
									HttpSession sessionObj,
									@RequestParam("username") String loginUsername,
									@RequestParam("password") String loginPassword) {
		System.out.println("Username: "+loginUsername);
		System.out.println("Password: "+loginPassword);
		
		// validate that username and password is correct
		// (there exists a user with the same name and password combo)
		Optional<Customer> foundCustomer = customerService.findByNameAndPassword(loginUsername, loginPassword);
		if (foundCustomer.isPresent()) {
			// if a valid customer is found
			Customer gotCustomer = foundCustomer.get();
			System.out.println(gotCustomer.getName());
			// username and password to be keyed into the login box can now be empty
			model.addAttribute("username","");
			model.addAttribute("password","");
			// no longer a wrong password
			model.addAttribute("showWrongPasswordError",false);
			// isLoggedIn is a boolean to indicate if the current HttpSession is logged in
			// Refer to this value for pages that require logins to access
			sessionObj.setAttribute("isLoggedIn",true);
			// HttpSession to save the customerId and name to reference
			// Refer to this value for pages that require logins to access
			// change:
			// 1.add customer attribute
			sessionObj.setAttribute("customer", gotCustomer);
			sessionObj.setAttribute("customerId", gotCustomer.getId());
			sessionObj.setAttribute("customerName", gotCustomer.getName());
			System.out.println("Current isLoggedIn status: "+model.getAttribute("isLoggedIn"));
			// Redirect to logstat (the 'main' page)
			return "redirect:/logstat";
		} else {
			// login invalid; return the user to the login page and ask them to re-type
			model.addAttribute("username",loginUsername);
			model.addAttribute("password","");
			model.addAttribute("showWrongPasswordError",true);
			// Redirect to login page
			return "login";
		}
	}
	
	// signup is the page for creating new accounts
	@GetMapping("/signup")
	public String signUpPage(Model model, HttpSession sessionObj) {
		// If the 'isLoggedIn' session object is not made assume not logged in
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			sessionObj.setAttribute("isLoggedIn",false);
		}
		// if you are already logged in redirect to the front
		//if ((boolean) sessionObj.getAttribute("isLoggedIn") == true) {
		//	return "redirect:/logstat";
		//}
		// create a new model for SignUp data
		model.addAttribute("signup",new SignUp());
		// uses 'create-account.html'
		return "create-account";
	}
	
	// After clicking the create new account on the signup page
	@PostMapping("/signup")
	public String createNewCustomer(@Valid @ModelAttribute SignUp signUp,
										BindingResult bindingResult,
										Model model) {
		// Print sign up form details for personal validation
		System.out.println("Username: "+signUp.getUsername());
		System.out.println("email: "+signUp.getEmail());
		System.out.println("contactNumber: "+signUp.getContactNumber());
		System.out.println("password1: "+signUp.getPassword1());
		System.out.println("password2: "+signUp.getPassword2());
		// If there are errors (ex. empty email, password not strong enough)
		// redirect back to sign up page
		if (bindingResult.hasErrors()) {
			System.out.println("Errors were found:");
			System.out.println("Errors found: " + bindingResult.getErrorCount());
			// Question: This could previously be placed after the bindingResult check outside in workshops.
			// However here I have to re-add.
			model.addAttribute("signup",signUp); // keep the form data
			// Question: In workshops re-adding bindingResult was unnecessary. However here it is necessary.
			model.addAttribute("org.springframework.validation.BindingResult.signup", bindingResult); // Add the binding result
			// uses 'create-account.html'
			return "create-account";
		}
		// if successful, create a new customer account
//		Customer newCustomer = new Customer();
//		newCustomer.setName(signUp.getUsername());
//		newCustomer.setEmail(signUp.getEmail());
//		newCustomer.setContactNumber(signUp.getContactNumber());
//		newCustomer.setPassword(signUp.getPassword1());
		customerService.addNew(signUp);
		// redirect back to front page
		return "redirect:/logstat";
	}
	
	// logstat is just a page for seeing if you are logged in or out
	@RequestMapping("/logstat")
	public String markHome(HttpSession sessionObj) {
		System.out.println("Current isLoggedIn status: "+sessionObj.getAttribute("isLoggedIn"));
		// create isLoggedIn value if not already created
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			System.out.println("Create New");
			sessionObj.setAttribute("isLoggedIn",false);
		}
		System.out.println("Current isLoggedIn status: "+(boolean) sessionObj.getAttribute("isLoggedIn"));
		// uses 'logstat.html'
		return "logstat";
	}
	
	// for logging out
	@RequestMapping("/logout")
	public String logoutOfAccount(HttpSession sessionObj) {
		// clear the current http session
		sessionObj.invalidate();
		// redirect to front page
		return "redirect:/logstat";
	}
}
