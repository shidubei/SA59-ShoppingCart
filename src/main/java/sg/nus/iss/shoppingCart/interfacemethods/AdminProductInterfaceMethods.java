package sg.nus.iss.shoppingCart.interfacemethods;

import sg.nus.iss.shoppingCart.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface AdminProductInterfaceMethods {

    List<Product> listProducts();

    void createProduct(String name, BigDecimal price, String category);

    void deleteProduct(int id);

    void updateProduct(int id, String name, BigDecimal price, String category);

    List<Product> findByName(String name);

}
