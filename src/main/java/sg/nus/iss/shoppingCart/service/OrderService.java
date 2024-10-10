package sg.nus.iss.shoppingCart.service;

import java.util.List;
import java.util.Optional;

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
	
	 @Autowired
	 private CustomerService customerService;
	
	//View Order History - retrieves all orders with a specific customer 
	@Transactional
	public List<Order> findOrdersByCustomer(Customer customer) {
		return orderRepo.findByCustomer(customer);
	}

	@Override
	public Optional<Order> findOrderDetailsForCustomer(Customer customer, int orderId) {
		return orderRepo.findByCustomerAndId(customer, orderId); 
	}

}
/* Creator: Azril
 * Date: 2024-10-10 */
