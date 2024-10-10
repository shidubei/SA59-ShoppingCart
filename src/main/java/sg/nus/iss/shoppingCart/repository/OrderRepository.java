
package sg.nus.iss.shoppingCart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.criteria.Order;
import sg.nus.iss.shoppingCart.model.Customer;

public interface OrderRepository extends JpaRepository<Order, Integer>{	
	//Get Order History - retrieves all orders with a specific customer 
	List<Order> findByCustomer (Customer customer);
		
	Optional<Order> findByCustomerAndId(Customer customer, Integer id); 
}

/* Creator: Azril
 * Date: 2024-10-10 */
