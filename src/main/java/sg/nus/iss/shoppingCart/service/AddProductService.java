package sg.nus.iss.shoppingCart.service;

import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

import java.util.List;

public interface  AddProductService {
    List<Product> findAllProducts();
    ShoppingCartItem addProduct(Product product);


}





