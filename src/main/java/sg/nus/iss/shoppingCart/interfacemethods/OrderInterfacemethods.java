package sg.nus.iss.shoppingCart.interfacemethods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.model.OrderDetails;
import sg.nus.iss.shoppingCart.model.Product;

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

	/**
	 * Creator:
	 * Date:14 Oct 2024
	 * Explain:
	 */
	void CreateOrder(Order order,OrderDetails orderDetails);

	/**
	 * Creator:
	 * Date:14 Oct 2024
	 * Explain:
	 */
	int getOrderItemCount(int id);

	/**
	 * Creator:
	 * Date:14 Oct 2024
	 * Explain:
	 */
	List<Product> getProductsInOrder(int id);



	/**
	 * Creator:
	 * Date:16 Oct 2024
	 * Explain:
	 */
	BigDecimal findOrderProductUnits(int orderId, int productId);
}
