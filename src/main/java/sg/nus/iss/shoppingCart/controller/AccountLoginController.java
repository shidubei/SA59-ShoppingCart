package sg.nus.iss.shoppingCart.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// This is for testing the interface change between login and logout

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import sg.nus.iss.shoppingCart.validation.SignUpValidator;
import sg.nus.iss.shoppingCart.interfacemethods.CustomerInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.dto.SignUp;
import sg.nus.iss.shoppingCart.service.CustomerService;
//change Controller to RestController
@RestController
@CrossOrigin(origins="http://localhost:3000")
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
	
	@InitBinder("signUp")
	private void initSignUpBinder(WebDataBinder binder) {
		// Other binding
		binder.addValidators(signUpValidator);
	}
	
	// Go to login screen
	@GetMapping("/login")
	public ResponseEntity<Customer> loginPage(Model model,HttpSession sessionObj) {
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
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// Change it to RestAPI Post Request
	// postmapping on login
	@PostMapping("/login")
	public ResponseEntity<Object> loginToAccount(Model model,
									HttpSession sessionObj,
									@RequestBody Customer customer) {
//		System.out.println("Username: "+loginUsername);
//		System.out.println("Password: "+loginPassword);
		
		// validate that username and password is correct
		// (there exists a user with the same name and password combo)
		// change, use RequestBody's customer name and password
		Optional<Customer> foundCustomer = customerService.findByNameAndPassword(customer.getName(), customer.getPassword());
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
			
			// HttpSession to save the customerId and name to reference
			// Refer to this value for pages that require logins to access
			// change:
			// 1.add customer attribute
			if(gotCustomer.getName().equals("admin")) {
				Map<String,Object> response = new HashMap<>();
				response.put("isAdmin",true);
				response.put("name","admin");
				return new ResponseEntity<>(response,HttpStatus.OK);
			}
			sessionObj.setAttribute("isLoggedIn",true);
			sessionObj.setAttribute("customer", gotCustomer);
			sessionObj.setAttribute("customerId", gotCustomer.getId());
			sessionObj.setAttribute("customerName", gotCustomer.getName());
			System.out.println("Current isLoggedIn status: "+sessionObj.getAttribute("isLoggedIn"));
			System.out.println("Current isLoggedIn status: "+sessionObj.getAttribute("customerId"));
			System.out.println("Current isLoggedIn status: "+sessionObj.getAttribute("customer"));
			// Redirect to logstat (the 'main' page)
			return new ResponseEntity<>(gotCustomer,HttpStatus.OK);
		} else {
			// login invalid; return the user to the login page and ask them to re-type
			model.addAttribute("username",customer.getName());
			model.addAttribute("password","");
			model.addAttribute("showWrongPasswordError",true);
			// Redirect to login page
			Map<String,String> errorResponse = new HashMap<>();
			errorResponse.put("ERROR","Invalid username or password,can not login");
			return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
		}
	}
	
	// signup is the page for creating new accounts
	@GetMapping("/signup")
	public ResponseEntity<?> signUpPage(Model model, HttpSession sessionObj) {
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
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/*
	 * */
	@PostMapping("/signup")
	public ResponseEntity<Object> createNewCustomer(@Valid @RequestBody SignUp signUp,
										BindingResult bindingResult,
										Model model) {
//		System.out.println("Username: "+signUp.getUsername());
//		System.out.println("email: "+signUp.getEmail());
//		System.out.println("contactNumber: "+signUp.getContactNumber());
//		System.out.println("password1: "+signUp.getPassword1());
//		System.out.println("password2: "+signUp.getPassword2());

		if (bindingResult.hasErrors()) {
			System.out.println("Errors were found:");
			System.out.println("Errors found: " + bindingResult.getErrorCount());
			model.addAttribute("signup",signUp); // keep the form data
			model.addAttribute("org.springframework.validation.BindingResult.signup", bindingResult); 
			// Add the binding result
			return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
		
		if(!signUp.getPassword1().equals(signUp.getPassword2())) {
			bindingResult.rejectValue("password2", "error.password2","password2 do not match");
			model.addAttribute("signup",signUp); // keep the form data
			model.addAttribute("org.springframework.validation.BindingResult.signup", bindingResult);
			Map<String,String> errorResponse = new HashMap<>();
			errorResponse.put("ERROR","Password1 not equals to Password2");// Add the binding result
			return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		customerService.addNew(signUp);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	// logstat is just a page for seeing if you are logged in or out
	@GetMapping("/customer")
	public ResponseEntity<Object> customerHome(HttpSession sessionObj) {
		System.out.println("Current isLoggedIn status: "+sessionObj.getAttribute("isLoggedIn"));
		// create isLoggedIn value if not already created
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			System.out.println("Create New");
			sessionObj.setAttribute("isLoggedIn",false);
		}
		System.out.println(sessionObj.getAttribute("customer"));
		Customer customer = (Customer)sessionObj.getAttribute("customer");
		if(customer == null) {
			Map<String,String> errorResponse = new HashMap<>();
			errorResponse.put("ERROR","Can Not Get Customer Info");
			return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
		}else {
			System.out.println("Current isLoggedIn status: "+(boolean) sessionObj.getAttribute("isLoggedIn"));
			return new ResponseEntity<>(customer,HttpStatus.OK);
		}
	}
	
	// for logging out
	@GetMapping("/logout")
	public ResponseEntity<?> logoutOfAccount(HttpSession sessionObj) {
		System.out.println("inLogout");
		// clear the current http session
		sessionObj.invalidate();
		// redirect to front page
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
