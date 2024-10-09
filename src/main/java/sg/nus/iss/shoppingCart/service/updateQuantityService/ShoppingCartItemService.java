package sg.nus.iss.shoppingCart.service.updateQuantityService;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemService {
    List<ShoppingCartItem> findAllShoppingCartItem();
    ShoppingCartItem updateQuantity(ShoppingCartItem shoppingCartItem);
}
