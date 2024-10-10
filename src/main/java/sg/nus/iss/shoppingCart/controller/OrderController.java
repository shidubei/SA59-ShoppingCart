package sg.nus.iss.shoppingCart.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.shoppingCart.interfacemethods.CustomerInterfacemethods;
import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;
import sg.nus.iss.shoppingCart.repository.OrderRepository;
import sg.nus.iss.shoppingCart.service.CustomerService;
import sg.nus.iss.shoppingCart.service.OrderService;

@Controller
@RequestMapping("/customer/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderInterfacemethods orderService; 
	
	@Autowired
	private CustomerRepository customerRepo; 
	
	@Autowired 
	private CustomerInterfacemethods customerService; 
	
	@Autowired
	public void setOrderService (OrderService oserviceImpl) {
		this.orderService = oserviceImpl;
	}
	
	@Autowired
	public void setCustomerService (CustomerService cserviceImpl) {
		this.customerService = cserviceImpl;
	}

	//Simple greeting message
	@GetMapping("/greeting" )
	public String greetingOrder(Model model) {
		System.out.println("This line is inside console for /greeting");
		model.addAttribute("message", "Welcome to order page!"); 
		return "/greeting-order";
	}
	
	@GetMapping("/vieworder/{customerId}")
	public String getOrdersByCustomer(@PathVariable int customerId, Model model) {
	    Customer customer = customerService.findCustomerById(customerId);
	    if (customer == null) {
	        model.addAttribute("message", "Customer not found.");
	        return "vieworder-unsuccessful"; 
	    }
	    List<jakarta.persistence.criteria.Order> orders = orderService.findOrdersByCustomer(customer);
	    if (orders.isEmpty()) {
	        model.addAttribute("message", "No orders found for this customer.");
	    } else {
	        model.addAttribute("message", "Order history for customer ID: " + customerId);
	        model.addAttribute("orders", orders);
	    }
	    return "vieworder"; 
	}
	
	
	//Create ORDER from SHOPPINGCART ITEM that has been checked-out
	@GetMapping("/create-order") 
	public String createOrder(Model model) {
		System.out.println("This line is from /create-order");
		model.addAttribute("order", new Order());
		return "create-order";
	}
	@PostMapping("/save-order")
	public String saveOrder(@ModelAttribute Order order) {
		System.out.println("This line is from /save-order");
		System.out.println("Order created: " + order.getId());
		return "redirect:/create-order";
	}
}

/* Creator: Azril
 * Date: 2024-10-10 */
