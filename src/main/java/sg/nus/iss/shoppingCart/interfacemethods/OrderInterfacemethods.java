package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;
import sg.nus.iss.shoppingCart.model.Order;

public interface OrderInterfacemethods {
	// some logic problem, the order should only show current customer's order
	// if show all order means that one customer can see another customer's order
	// soon show in browser
	public List<Order> getAllOrders();

}
