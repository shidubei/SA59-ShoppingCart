package sg.nus.iss.shoppingCart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.interfacemethods.AdminProductInterfaceMethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.repository.AdminProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminProductService implements AdminProductInterfaceMethods {

    // This @Autowired annotation is used to let Spring know that it needs to inject the AdminProductRepository bean into this class.
    @Autowired
    AdminProductRepository adminProductRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    // This method is used to create a new product.
    // It overrides the empty method in the interface and uses the save method from the AdminProductRepository class to save the product.
    @Override
    public void createProduct(String name, BigDecimal price, String category) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        adminProductRepository.save(product);
    }

    // This method is used to list all products.
    // It overrides the empty method in the interface and uses the findAllProducts method from the AdminProductRepository class to get all products.
    @Override
    public List<Product> listProducts() {
        return adminProductRepository.findAll();
    }

    // This method is used to delete a product.
    // It overrides the empty method in the interface and uses the deleteById method from the AdminProductRepository class to delete the product.
    @Override
    public void deleteProduct(int id) {
        adminProductRepository.deleteById(id);
    }

    // This method is used to update a product.
    // It overrides the empty method in the interface and uses the findById method from the AdminProductRepository class to find the product by id.
    // It then updates the product with the new values and saves it using the save method from the AdminProductRepository class.
    // The save method automatically updates the product if it can find and detect an existing copy.
    @Override
    @Transactional
    public void updateProduct(int id, String name, BigDecimal price, String category,boolean isSelling) {
        Product product = entityManager.find(Product.class, id);
        if(product!=null) {
        	product.setName(name);
	        product.setPrice(price);
	        product.setCategory(category);
	        product.setIsSelling(isSelling);
        }else {
        	throw new RuntimeException("Product Not Found");
        }
    }

    // This method is used to find a product by name.
    // It overrides the empty method in the interface and uses the findByName method from the AdminProductRepository class to find the product by name.
    @Override
    public List<Product> findByName(String name) {
        return adminProductRepository.findByName(name);
    }

    // This method is used to get a product by id.
    // It uses the findById method from the AdminProductRepository class to find the product by id.
    public Product getProductById(int id) {
        return adminProductRepository.findById(Math.toIntExact(id)).orElse(null);
    }
}
