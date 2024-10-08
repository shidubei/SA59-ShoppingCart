package sg.nus.iss.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * Creator:
 * Date:7 Oct 2024
 * Explain:
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;

@Controller
public class CustomerController {
	@Autowired
	CustomerRepository customerRepo;
	// just to test
	@GetMapping("/customer")
	public String addCustomer(HttpSession session,Model model) {
		Customer customer = new Customer();
		customer.setUsername("testDelete3");
		customer.setPassword("fagagafnvFfaAGAa");
		customer.setEmail("testDelete3@mail.com");
		customer.setContactNumber("ngaabjGAGAsgnkag");
		customerRepo.save(customer);
		session.setAttribute("customer", customer);
//		session.setAttribute("customerId", customer.getId());
		return "add-customer-success";
	}
}
