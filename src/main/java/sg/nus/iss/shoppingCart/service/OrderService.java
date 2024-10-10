package sg.nus.iss.shoppingCart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;
import sg.nus.iss.shoppingCart.repository.OrderRepository;

@Service
@Transactional
public class OrderService implements OrderInterfacemethods{
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	//View Order History - retrieves all orders with a specific customer 
	@Transactional
	public List<Order> findOrdersByCustomer(Customer customer) {
		return orderRepo.findByCustomer(customer);
	}
	
	//View Order History with sort by Order date
	@Override
	@Transactional
	public List<Order> findOrdersByCustomerSortedByDate(Customer customer) {
		return orderRepo.findByCustomerOrderByOrderDate(customer); 
	}	
	
	//Filter by status 
	@Override
	@Transactional
	public List<Order> findOrdersByCustomerAndStatus(Customer customer, String status) {
		return orderRepo.findByCustomerAndStatus(customer, status);
	}
	
	//Sort by total amount
	@Override
	@Transactional
	public List<Order> findOrdersByCustomerSortedByTotalAmount(Customer customer) {
		return orderRepo.findByCustomerOrderByTotalAmountDesc(customer);
	}
	

//	//Implement findOrder using JPA repository findAll
//	@Override
//	public List<Order> findAllOrder() {
//		
//		return orderRepository.findAll();
//	}
//
//	//Implement findOrderById using JPA repository findById
//	@Override
//    public Optional<Order> findOrderById(int id) {
//        return orderRepository.findById(id);
//    }
//
//	@Override
//	public void saveOrder(Order order) {
//        orderRepository.save(order);		
//	}

}
/* Creator: Azril
 * Date: 2024-10-10 */
