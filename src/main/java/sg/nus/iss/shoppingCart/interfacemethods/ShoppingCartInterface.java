package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;

import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

/**
 * Creator: Kelly&LiuRuiHan&ZhongYi
 */
public interface ShoppingCartInterface {
	/*
	 * Creator: Kelly
	 * Date: 05-10-2024
	 * Explain: Get user's shopping cart function
	 */
	public ShoppingCart getShoppingCart(int id);
	
	
	List<ShoppingCartItem> findAllShoppingCartItem();

	List<ShoppingCartItem> findByProductID(int id);

	ShoppingCartItem updateQuantity(ShoppingCartItem shoppingCartItem);

	public void deleteProduct(int product_id,int id);


	ShoppingCartItem checkIfExistInShoppingCart(int product_id, ShoppingCart sc);


	void saveShoppingCartItem(ShoppingCartItem sci);


	void updateShoppingCartItemQty(int id, int add_qty);

	int countItem(int id);
}
