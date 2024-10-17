package sg.nus.iss.shoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import sg.nus.iss.shoppingCart.repository.ShoppingCartItemRepository;

/**
 * Creator: Kelly&LiuRuiHan&
 * Explain: This is the Server Layer Implement for Address Service
 */
@Service
@Transactional
public class ShoppingCartService implements ShoppingCartInterface{
	
	@Autowired
	private ShoppingCartItemRepository shoppingCartItemRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@PersistenceContext
	private EntityManager entityManager;
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
	public List<ShoppingCartItem> findAllShoppingCartItem() {
		return List.of();
	}

	@Override
	public List<ShoppingCartItem> findByProductID(int id) {
		return List.of();
	}

	@Override
	public ShoppingCartItem updateQuantity(ShoppingCartItem shoppingCartItem) {
		return null;
	}
	
	@Transactional
	@Override
	public void deleteProduct(int product_id,int id) {
		shoppingCartItemRepo.deleteByCategory(product_id, id);;
	}
	@Override
	public ShoppingCartItem checkIfExistInShoppingCart(int product_id,ShoppingCart sc) {
		// if find return find product
		// else return null
		int shopping_cart_id=sc.getId();
		return shoppingCartItemRepo.findByProductId(product_id,shopping_cart_id);
	}
	@Override
	public void saveShoppingCartItem(ShoppingCartItem sci) {
		shoppingCartItemRepo.save(sci);
	}
	
	@Override
	@Transactional
	public void updateShoppingCartItemQty(int id,int add_qty) {
		ShoppingCartItem sc = entityManager.find(ShoppingCartItem.class, id);
		if(sc!=null) {
			sc.setQuantity(sc.getQuantity()+add_qty);
		}else {
			throw new RuntimeException("ShoppingCartItem not found for id");
		}
	}
	@Override
	public int countItem(int id) {
		return shoppingCartItemRepo.countItem(id);
	}
}
