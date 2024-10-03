package sg.nus.iss.shoppingCart.controller;

// This is for testing the interface change between login and logout

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;


//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.validation.BindingResult;


@Controller
public class TestMarkController {

	@RequestMapping("/create_account")
	public String getSearchPage() {
		return "create-account";
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
