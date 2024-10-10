package sg.nus.iss.javaspring.deleteproduct.service;

import sg.nus.iss.javaspring.deleteproduct.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemService {
    List<ShoppingCartItem> findAllShoppingCartItem();
    void deleteProduct(int id);
}
