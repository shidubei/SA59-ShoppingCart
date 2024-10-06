
package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.Order;


public interface OrderRepository extends JpaRepository<Order,Integer> {

}
