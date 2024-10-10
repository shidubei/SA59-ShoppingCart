
package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.criteria.Order;
import sg.nus.iss.shoppingCart.model.Customer;

public interface OrderRepository extends JpaRepository<Order, Integer>{	
	//Get Order History - retrieves all orders with a specific customer 
	List<Order> findByCustomer (Customer customer);
	
//	Get Order History - retrieves orders with a specific customer by order date 
	List<Order> findByCustomerOrderByOrderDate(Customer customer);
//	
//	Filter by status
	List<Order> findByCustomerAndStatus (Customer customer, String status);
//	
//	Sort by total amount
	List<Order> findByCustomerOrderByTotalAmountDesc (Customer customer); 
	
	
//    Optional<Order> findById(Integer id);  // Default Spring Data JPA method
//
//	@Query("SELECT o FROM Order o WHERE o.orderDate = :orderDate")
//	List<Order> getByOrderDate (@Param("orderDate") String orderDate);

}

/* Creator: Azril
 * Date: 2024-10-10 */
