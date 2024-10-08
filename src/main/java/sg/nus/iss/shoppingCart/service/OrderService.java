package sg.nus.iss.shoppingCart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.repository.OrderRepository;

// change: 
// 1.OrderService Implements OrderInterfacemethods
// 2.@Override the getAllOrders methods

@Service
public class OrderService implements OrderInterfacemethods{
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

}
