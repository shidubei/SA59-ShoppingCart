package sg.nus.iss.javaspring.updatequantity.service;

import sg.nus.iss.javaspring.updatequantity.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemService {
    List<ShoppingCartItem> findAllShoppingCartItem();
    ShoppingCartItem updateQuantity(ShoppingCartItem shoppingCartItem);
}
