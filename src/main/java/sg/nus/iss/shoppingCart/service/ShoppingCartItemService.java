package sg.nus.iss.shoppingCart.service;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemService {
    List<ShoppingCartItem> findAllShoppingCartItem();
    void deleteProduct(int id);
}
