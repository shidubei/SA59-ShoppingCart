package sg.nus.iss.shoppingCart.service;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;


public interface AddQuantityService {
    ShoppingCartItem findByProductId(int id);
    ShoppingCartItem add(ShoppingCartItem shoppingCartItem);
}
