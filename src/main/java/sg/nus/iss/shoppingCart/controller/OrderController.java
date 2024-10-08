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

import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.repository.OrderRepository;
import sg.nus.iss.shoppingCart.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	// change: 
	// 1.use service to implement service, not direct use service
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderInterfacemethods orderService; 
	
	@Autowired
	private void setOrderService(OrderService orderService) {
		this.orderService=orderService;
	}
	
	
	//Simple greeting message
	@GetMapping("/greeting" )
	public String greetingOrder(Model model) {
		System.out.println("This line is inside console");
		model.addAttribute("message", "Welcome to order page!"); 
		return "/greeting-order";
	}
	
	//View all orders in system
	@GetMapping("/display-order")
	public String displayOrder(Model model) {
		System.out.println("This line is from /display-order");
		List<Order> orders = orderService.findAllOrder();
		model.addAttribute("message", "Order List");
		model.addAttribute("orders", orders);
		return "/display-order";
		}
	
	//View orders by Order ID 
	//GOT ISSUE 
	@GetMapping("/display-order/displayByOrderId/{id}")
	public String displayOrderByOrderId(@PathVariable int id) {
		System.out.println("This line is from /display-order/displayByOrderId");

		List<Order> orders = orderRepository.findById(id);
		if(!orders.isEmpty()) {
			orders.forEach(order -> { 
			System.out.println(order.getId());
			System.out.println(order.getOrderDate());
			System.out.println(order.getStatus());
			System.out.println(order.getProducts());
			});
			return "/display-order-orderId";
		}
	    //Order Id incorrect, redirect to the main order display page
		return "redirect:/order/display-order";
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
