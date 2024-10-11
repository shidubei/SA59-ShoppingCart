package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;
import java.util.Optional;


import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;

public interface OrderInterfacemethods {
	/*Standard CRUD naming conventions 
	 * CREATE: save, create, add
	 * READ/RETRIEVE: find, get, retrieve
	 * UPDATE: update
	 * DELETE: delete, remove
	 */
	 
	//View Order History - retrieves all orders with a specific customer 
	public List<Order> findOrdersByCustomer(Customer customer); 
	
	public Optional<Order> findOrderDetailsForCustomer (Customer customer, int id);
}
