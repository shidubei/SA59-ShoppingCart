package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.nus.iss.shoppingCart.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
	@Query("select p from Product p")
	public List<Product> findAllProducts();
}
