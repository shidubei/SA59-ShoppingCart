package sg.nus.iss.shoppingCart.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
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
	public String greetingOrder(HttpSession session, Model model) {
		
	    // Retrieve customerId from session
	     Integer customerId = (Integer) session.getAttribute("customerId");
		System.out.println("This line is inside console for /greeting");
		model.addAttribute("message", "Welcome to order page!"); 
	    model.addAttribute("customerId", customerId);  // Use the passed customerId
		return "/greeting-order";
	}
	
	
	@GetMapping("/vieworder/{customerId}")
	public String getOrdersByCustomer(HttpSession session, Model model) {
		
	    Integer customerId = (Integer) session.getAttribute("customerId");
	    // Fetch the customer and their orders using the customerId
	    Customer customer = customerService.findCustomerById(customerId);
	    if (customer == null) {
	        model.addAttribute("message", "You need to log in to view your orders!");
	        return "redirect:/login"; 
	    }
	    
	    if (customer == null) {
	        model.addAttribute("message", "Customer not found.");
	        return "redirect:/login";
	    }
	    
	    List<Order> orders = orderService.findOrdersByCustomer(customer);
	    if (orders.isEmpty()) {
	        model.addAttribute("message", "No orders found for this customer.");
	    } else {
	        model.addAttribute("message", "Order history for customer ID: " + customerId);
	        model.addAttribute("orders", orders);
	    }
	    return "vieworder"; 
	}
	
	@GetMapping("/vieworderdetail/{orderId}")
	public String getOrderDetailsByCustomer(@PathVariable("orderId") int orderId, HttpSession session, Model model) {
	    Integer customerId = (Integer) session.getAttribute("customerId");
	    Customer customer = customerService.findCustomerById(customerId);

	    if (customerId == null) {
	        model.addAttribute("message", "You need to log in to view order details!");
	        return "redirect:/login";
	    }
	    
	    // Fetch the order details for the specific customer and order
	    Optional<Order> order = orderService.findOrderDetailsForCustomer(customer, orderId);

	    // Check if the order is found and belongs to the customer
	    if (order.isPresent()) {
	        model.addAttribute("order", order.get());
	        return "vieworderdetail";
	    } else {
	        return "redirect:/customer/order/vieworder";  // Redirect if order is not found
	    }
	}
//	
//	//Create ORDER from SHOPPINGCART ITEM that has been checked-out
//	@GetMapping("/create-order") 
//	public String createOrder(Model model) {
//		System.out.println("This line is from /create-order");
//		model.addAttribute("order", new Order());
//		return "create-order";
//	}
//	@PostMapping("/save-order")
//	public String saveOrder(@ModelAttribute Order order) {
//		System.out.println("This line is from /save-order");
//		System.out.println("Order created: " + order.getId());
//		return "redirect:/create-order";
//	}
}

/* Creator: Azril
 * Date: 2024-10-10 */
