package sg.nus.iss.shoppingCart.interfacemethods;

import sg.nus.iss.shoppingCart.model.Product;
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
	
	/**
	 * Creator: ZhongYi
	 * Date:9 Oct 2024
	 * Explain: when we add Product to ShoppingCart,first it will check the product
	 *  if exist in ShoppingCart 
	 */
	public ShoppingCartItem checkIfExistInShoppingCart(int product_id,ShoppingCart sc);
	
	public void saveShoppingCartItem(ShoppingCartItem sci);
	
	public void updateShoppingCartItemQty(int id,int qty);
}