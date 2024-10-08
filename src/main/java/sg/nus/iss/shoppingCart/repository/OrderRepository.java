
package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.Order;


public interface OrderRepository extends JpaRepository<Order,Integer> {
	List<Order> findById (int id);

}
