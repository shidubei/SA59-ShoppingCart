package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.shoppingCart.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
	@Query("select p from Product p")
	public List<Product> findAllProducts();
	
	@Query("select p from Product p where p.id=:id")
	public Product findByProductId(@Param("id") int id);
}
