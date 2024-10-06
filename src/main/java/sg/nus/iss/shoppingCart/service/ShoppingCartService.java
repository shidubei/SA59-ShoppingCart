package sg.nus.iss.shoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.interfacemethods.ShoppingCartInterface;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;
//import sg.nus.iss.shoppingCart.model.OrderDetails;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;
import sg.nus.iss.shoppingCart.repository.OrderRepository;

@Service
@Transactional
public class ShoppingCartService implements ShoppingCartInterface{

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private OrderRepository orderRepo;
	/*
	 * Creator: Kelly
	 * Date: 05-10-2024
	 * Explain: Get user's shopping cart, if exist return the ShoppingCart object
	 * If not return null
	 */
	@Override
	@Transactional
	public ShoppingCart getShoppingCart(int id) {
		Optional<Customer> customerOptional = customerRepo.findById(id);

		if(customerOptional.isPresent()) {
			Customer customer = customerOptional.get();

			ShoppingCart sc = customer.getShoppingCart();

			return sc;

		}

		else {
			return null;
		}
	}
	@Override
	public void CheckOut(int id, ShoppingCart sc) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	@Override
	@Transactional
	public void CheckOut(int id, ShoppingCart sc) {
		List<ShoppingCartItem> items = sc.getShoppingCartItems();
		Order o = new Order();
		Customer c = customerRepo.findById(id).get();
		o.setCustomer(c);
		for(ShoppingCartItem i:items) {
			OrderDetails od = new OrderDetails();
			od.setProduct(i.getProduct());
			od.setQuantity(i.getQuantity());
			o.getOrderDetails().add(od);
		}
	
	}
	*/
}
