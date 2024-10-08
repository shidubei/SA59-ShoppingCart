package sg.nus.iss.shoppingCart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.repository.ProductRepository;

@Service
public class ProductService implements ProductInterfacemethods{
	@Autowired
	ProductRepository productRepo;
	
	@Override
	public List<Product> listProducts() {
		return productRepo.findAll();
	}

}
