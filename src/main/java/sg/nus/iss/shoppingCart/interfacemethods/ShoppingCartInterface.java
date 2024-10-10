package sg.nus.iss.shoppingCart.interfacemethods;

import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartInterface {
	/*
	 * Creator: Kelly
	 * Date: 05-10-2024
	 * Explain: Get user's shopping cart function
	 */
	public ShoppingCart getShoppingCart(int id);
	
	public void CheckOut(int id, ShoppingCart sc);

	List<ShoppingCartItem> findAllShoppingCartItem();

	List<ShoppingCartItem> findByProductID(int id);

	ShoppingCartItem updateQuantity(ShoppingCartItem shoppingCartItem);

	public void deleteProduct(int id);
}
