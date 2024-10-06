package sg.nus.iss.shoppingCart.controller;

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

//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;


import sg.nus.iss.shoppingCart.validation.SignUpValidator;
import sg.nus.iss.shoppingCart.model.SignUp;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;

@Controller
public class TestMarkController {

	@Autowired
	private SignUpValidator signUpValidator;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@InitBinder
	private void initSignUpBinder(WebDataBinder binder) {
		// Other binding
		binder.addValidators(signUpValidator);
	}
	
	@GetMapping("/login")
	public String loginPage(Model model,HttpSession sessionObj) {
		// if you are already logged in redirect to the front
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			sessionObj.setAttribute("isLoggedIn",false);
		}
		if ((boolean) sessionObj.getAttribute("isLoggedIn") == true) {
			return "redirect:/logstat";
		}
		// add model attributes
		model.addAttribute("username","");
		model.addAttribute("password","");
		model.addAttribute("showWrongPasswordError",false);
		return "login";
	}
	
	@PostMapping("/login")
	//@RequestMapping("/login")
	public String loginToAccount(Model model,
									HttpSession sessionObj,
									@RequestParam("username") String loginUsername,
									@RequestParam("password") String loginPassword) {
		System.out.println("Username: "+loginUsername);
		System.out.println("Password: "+loginPassword);
		
		// validate that username and password is correct (there exists a user with the same name and password combo
		Customer foundCustomer = customerRepo.findByNameAndPassword(loginUsername, loginPassword);
		if (foundCustomer != null) {
			model.addAttribute("username","");
			model.addAttribute("password","");
			model.addAttribute("showWrongPasswordError",false);
			sessionObj.setAttribute("isLoggedIn",true);
			sessionObj.setAttribute("customerId", foundCustomer.getId());
			sessionObj.setAttribute("customerName", foundCustomer.getName());
			System.out.println("Current isLoggedIn status: "+model.getAttribute("isLoggedIn"));
			return "redirect:/logstat";
		} else {
			// login invalid; return the user to the login page and ask them to re-type
			model.addAttribute("username",loginUsername);
			model.addAttribute("password","");
			model.addAttribute("showWrongPasswordError",true);
			return "login";
		}
		//return "login";
	}
	
	@GetMapping("/signup")
	public String signUpPage(Model model, HttpSession sessionObj) {
		// if you are already logged in redirect to the front
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			sessionObj.setAttribute("isLoggedIn",false);
		}
		if ((boolean) sessionObj.getAttribute("isLoggedIn") == true) {
			return "redirect:/logstat";
		}
		model.addAttribute("signup",new SignUp());
		return "create-account";
	}
	
	@PostMapping("/signup")
	public String createNewCustomer(@Valid @ModelAttribute SignUp signUp,
										BindingResult bindingResult,
										Model model) {
		System.out.println("Username: "+signUp.getUsername());
		System.out.println("email: "+signUp.getEmail());
		System.out.println("contactNumber: "+signUp.getContactNumber());
		System.out.println("password1: "+signUp.getPassword1());
		System.out.println("password2: "+signUp.getPassword2());
		if (bindingResult.hasErrors()) {
			System.out.println("Errors were found:");
			System.out.println("Errors found: " + bindingResult.getErrorCount());
			// Question: This could previously be placed after the bindingResult check outside in workshops.
			// However here I have to re-add.
			model.addAttribute("signup",signUp); // keep the form data
			// Question: In workshops re-adding bindingResult was unnecessary. However here it is necessary.
			model.addAttribute("org.springframework.validation.BindingResult.signup", bindingResult); // Add the binding result
			return "create-account";
		}
		//model.addAttribute("signup",signUp); // keep the form data
		// if successful, create a new customer account
		Customer newCustomer = new Customer();
		newCustomer.setName(signUp.getUsername());
		newCustomer.setEmail(signUp.getEmail());
		newCustomer.setContactNumber(signUp.getContactNumber());
		newCustomer.setPassword(signUp.getPassword1());
		return "redirect:/logstat";
	}
	
	@RequestMapping("/logstat")
	public String markHome(HttpSession sessionObj) {
		System.out.println("Current isLoggedIn status: "+sessionObj.getAttribute("isLoggedIn"));
		if (sessionObj.getAttribute("isLoggedIn")==null) {
			System.out.println("Create New");
			sessionObj.setAttribute("isLoggedIn",false);
		}
		System.out.println("Current isLoggedIn status: "+(boolean) sessionObj.getAttribute("isLoggedIn"));
		return "logstat";
	}
	
	
	@RequestMapping("/logout")
	public String logoutOfAccount(HttpSession sessionObj) {
		sessionObj.setAttribute("isLoggedIn",false);
		sessionObj.setAttribute("customerId", null);
		return "redirect:/logstat";
	}
	
	//@RequestMapping("/mark_login")
	//public String whenLogin(Model model) {
	//	model.addAttribute("isLoggedIn",true);
	//	System.out.println("isLoggedIn true");
	//	return "logstat";
	//}
	
	//@RequestMapping(value="/mark_logout")
	//public String whenLogout(Model model) {
	//	model.addAttribute("isLoggedIn",false);
	//	System.out.println("isLoggedIn false");
	//	return "logstat";
	//}
	
}
