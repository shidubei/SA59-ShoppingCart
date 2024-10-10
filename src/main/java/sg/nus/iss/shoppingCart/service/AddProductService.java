package sevice;

import model.Product;
import model.ShoppingCartItem;

import java.util.List;

public interface  AddProductService {
    List<Product> findAllProducts();
    ShoppingCartItem addProduct(Product product);


}





