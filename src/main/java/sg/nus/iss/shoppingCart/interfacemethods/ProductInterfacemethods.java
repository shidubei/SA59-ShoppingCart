package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.repository.ProductRepository;


public interface ProductInterfacemethods {
	/**
	 * Creator: ZhongYi
	 * Date:5 Oct 2024
	 * Explain: this interface methods is about list product
	 */
	public List<Product> listProducts(); 
}
