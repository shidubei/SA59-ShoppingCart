
package sg.nus.iss.shoppingCart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;


public interface OrderRepository extends JpaRepository<Order,Integer> {
	//Get Order History - retrieves all orders with a specific customer 
	public List<Order> findByCustomer (Customer customer);
		
	public Optional<Order> findByCustomerAndId(Customer customer, Integer id); 
}
