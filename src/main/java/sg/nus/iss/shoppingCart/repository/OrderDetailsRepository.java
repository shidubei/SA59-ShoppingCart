/**
 * 
 */
package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.shoppingCart.model.OrderDetails;
import sg.nus.iss.shoppingCart.model.Product;

/**
 * Creator:
 * Date:14 Oct 2024
 * Explain:
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer> {
	@Query("select count(od) from OrderDetails od where od.order.id = :id")
	public int countItem(@Param("id") int id);
	
	@Query("select od.product from OrderDetails od where od.order.id = :id")
	public List<Product> findProductsByOrderId(@Param("id") int id);
}
