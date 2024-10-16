package sg.nus.iss.shoppingCart.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.shoppingCart.interfacemethods.CustomerInterfacemethods;
import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.model.OrderDetails;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;
import sg.nus.iss.shoppingCart.repository.OrderRepository;
import sg.nus.iss.shoppingCart.service.CustomerService;
import sg.nus.iss.shoppingCart.service.OrderService;

@RestController
@RequestMapping("/customer/order")
public class OrderController {

//	@Autowired
//	private OrderRepository orderRepository;

	@Autowired
	private OrderInterfacemethods orderService; 
	
//	@Autowired
//	private CustomerRepository customerRepo; 
	
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
	public String greetingOrder() {
		
//	    // Retrieve customerId from session
//	     Integer customerId = (Integer) session.getAttribute("customerId");
//		System.out.println("This line is inside console for /greeting");
//		model.addAttribute("message", "Welcome to order page!"); 
//	    model.addAttribute("customerId", customerId);  // Use the passed customerId
		return "Welcome to order page";
	}
	
	
	@GetMapping("/vieworder")
	public ResponseEntity<List<Map<String,Object>>> getOrdersByCustomer(HttpSession session, Model model) {
		
	    int customerId = (int) session.getAttribute("customerId");
	    // Fetch the customer and their orders using the customerId
	    // change:
	    // 1.use mark's code, return a Optional<Customer> Type
	    Optional<Customer> customer = customerService.findById(customerId);
	    
	    if (!customer.isPresent()) {
	        model.addAttribute("message", "You need to log in to view your orders!");
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	    }
	    // Question:
	    // 1.why you need two If statement;
//	    if (!customer.isPresent()) {
//	        model.addAttribute("message", "Customer not found.");
//	        return "redirect:/login";
//	    }
//	    
	    List<Order> orders = orderService.findOrdersByCustomer((Customer)customer.get());
	    orders.forEach(order->System.out.println(order));
	    List<Map<String,Object>> orderDatas = new ArrayList<>();
	    for (Order order : orders) {
	    	Map<String,Object> orderData = new HashMap<>();
	    	orderData.put("orderDate",order.getOrderDate());
	    	orderData.put("totalPrice", order.getTotalAmount());
	    	int orderItemCount = orderService.getOrderItemCount(order.getId());
	    	orderData.put("totalItems", orderItemCount);
	    	List<Product> productsInOrder = orderService.getProductsInOrder(order.getId());
	    	List<Map<String,Object>> productInfos = new ArrayList<>();
	    	for(Product product : productsInOrder) {
	    		Map<String,Object> productInfo = new HashMap<>();
	    		productInfo.put("name", product.getName());
	    		// we only store each products unit price in orderdetials table
	    		BigDecimal units = orderService.findOrderProductUnits(order.getId(), product.getId());
	    		// use units divide product.price to get quantity.
	    		BigDecimal quantity = units.divideToIntegralValue(product.getPrice());
	    		productInfo.put("quantity",quantity);
	    		productInfo.put("price", units);
	    		productInfos.add(productInfo);
	    	}
	    	orderData.put("items", productInfos);
	    	orderDatas.add(orderData);
	    }
	    if (orders.isEmpty()) {
	        model.addAttribute("message", "No orders found for this customer.");
	    } else {
	        model.addAttribute("message", "Order history for customer ID: " + customerId);
	        model.addAttribute("orders", orders);
	    }
	    return new ResponseEntity<>(orderDatas,HttpStatus.OK); 
	}
	
	@GetMapping("/vieworderdetail/{orderId}")
	public ResponseEntity<?> getOrderDetailsByCustomer(@PathVariable("orderId") int orderId, HttpSession session, Model model) {
	    Integer customerId = (Integer) session.getAttribute("customerId");
	    Optional<Customer> customer = customerService.findById(customerId);

	    if (customerId == null) {
	        model.addAttribute("message", "You need to log in to view order details!");
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    
	    // Fetch the order details for the specific customer and order
	    Optional<Order> order = orderService.findOrderDetailsForCustomer((Customer)customer.get(), orderId);

	    // Check if the order is found and belongs to the customer
	    if (order.isPresent()) {
	        model.addAttribute("order", order.get());
	        return new ResponseEntity<>(HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Redirect if order is not found
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