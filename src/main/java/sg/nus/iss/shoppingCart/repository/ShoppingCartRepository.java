
package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer>{


}
