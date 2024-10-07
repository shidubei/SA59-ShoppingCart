/**
 * 
 */
package sg.nus.iss.shoppingCart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Creator: Zhong Yi
 * Date:7 Oct 2024
 * Explain: this is a controller to handle address request;
 */
@Controller
@RequestMapping("/customer")
public class AddressController {
	@GetMapping("/address")
	public String listAddress(Model model) {
		return "display-address";
	}
}
