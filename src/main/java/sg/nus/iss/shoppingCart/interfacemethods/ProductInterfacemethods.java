package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;

import sg.nus.iss.shoppingCart.model.Product;

public interface ProductInterfacemethods {
	public List<Product> listProducts();
	public Product getProduct(int id);

	List<Product> listSellingProducts();

	List<Product> listProductByCategory(String category);
	
	List<Product> listProdcutByNameAndCategory(String name,String category);
	
}
