package sevice;

import model.Product;
import model.ShoppingCartItem;

import java.util.List;

public interface AddQuantityService {
    ShoppingCartItem findByProductId(int id);
    ShoppingCartItem add(ShoppingCartItem shoppingCartItem);
}
