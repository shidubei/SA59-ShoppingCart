package sg.nus.iss.shoppingCart.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.service.OrderService;

@Controller
public class OrderController {
	
	// change: 
	// 1.use service to implement service, not direct use service
	@Autowired
	private OrderInterfacemethods orderService; 
	
	@Autowired
	private void setOrderService(OrderService orderService) {
		this.orderService=orderService;
	}
	
	
	// /orders/view to show all the orders in viewOrderList Page
	@GetMapping("/orders/view")
	public String view(Model model) {
		    List<Order> orders = orderService.getAllOrders();
		    model.addAttribute("orders", orders);
		    return "viewOrderList";  
	}
}
