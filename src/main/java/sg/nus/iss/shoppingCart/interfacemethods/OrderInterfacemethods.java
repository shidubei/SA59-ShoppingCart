package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;

import jakarta.persistence.criteria.Order;
import sg.nus.iss.shoppingCart.model.Customer;

public interface OrderInterfacemethods {
	/*Standard CRUD naming conventions 
	 * CREATE: save, create, add
	 * READ/RETRIEVE: find, get, retrieve
	 * UPDATE: update
	 * DELETE: delete, remove
	 */
	 
	//View Order History - retrieves all orders with a specific customer 
	public List<Order> findOrdersByCustomer(Customer customer); 
	
	//View Order History with sort by Order date
	public List<Order> findOrdersByCustomerSortedByDate(Customer customer);
	
	//	Filter by status
	public List<Order> findOrdersByCustomerAndStatus(Customer customer, String status);
	
	//	Sort by total amount
	public List<Order> findOrdersByCustomerSortedByTotalAmount(Customer customer);

}

/* Creator: Azril
 * Date: 2024-10-10 */
