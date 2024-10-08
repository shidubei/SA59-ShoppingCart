package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;
import sg.nus.iss.shoppingCart.model.Order;

public interface OrderInterfacemethods {
	public List<Order> findAllOrder();
	
	public Order findOrderById(Integer id);

}
