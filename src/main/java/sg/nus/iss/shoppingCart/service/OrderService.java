package sg.nus.iss.shoppingCart.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.repository.OrderRepository;

// change: 
// 1.OrderService Implements OrderInterfacemethods
// 2.@Override the getAllOrders methods

@Service
@Transactional
public class OrderService implements OrderInterfacemethods{
	@Autowired
	private OrderRepository orderRepository;
	
	//Implement findOrderById using JPA repository findById
	@Override
	public List<Order> findAllOrder() {
		
		return orderRepository.findAll();
	}

	//Implement findOrderById using JPA repository findById
	@Override
	public Order findOrderById(Integer id) {
		return orderRepository.findById(id).get();
	}

}
