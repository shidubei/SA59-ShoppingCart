package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;

import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

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

	public void deleteProduct(int product_id,int id);

	/**
	 * Creator:
	 * Date:10 Oct 2024
	 * Explain:
	 */
	ShoppingCartItem checkIfExistInShoppingCart(int product_id, ShoppingCart sc);

	/**
	 * Creator:
	 * Date:10 Oct 2024
	 * Explain:
	 */
	void saveShoppingCartItem(ShoppingCartItem sci);

	/**
	 * Creator:
	 * Date:10 Oct 2024
	 * Explain:
	 */
	void updateShoppingCartItemQty(int id, int add_qty);

	/**
	 * Creator:
	 * Date:14 Oct 2024
	 * Explain:
	 */
	int countItem(int id);
}
