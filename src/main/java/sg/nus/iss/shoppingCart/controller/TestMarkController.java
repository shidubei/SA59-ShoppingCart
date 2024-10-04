package sg.nus.iss.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;

// This is for testing the interface change between login and logout

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

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

@Controller
public class TestMarkController {

	@Autowired
	private SignUpValidator signUpValidator;
	
	@InitBinder
	private void initSignUpBinder(WebDataBinder binder) {
		// Other binding
		binder.addValidators(signUpValidator);
	}
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
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
		return "general-success";
	}
	
	@GetMapping("/mark_login")
	public String whenLogin(Model model) {
		model.addAttribute("isLoggedIn",true);
		System.out.println("isLoggedIn true");
		return "logstat";
	}
	
	@RequestMapping(value="/mark_logout")
	public String whenLogout(Model model) {
		model.addAttribute("isLoggedIn",false);
		System.out.println("isLoggedIn false");
		return "logstat";
	}
	
}
