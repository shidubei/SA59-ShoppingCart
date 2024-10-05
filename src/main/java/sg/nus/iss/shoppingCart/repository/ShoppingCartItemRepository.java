package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

public interface ShoppingCartItemRepository extends JpaRepository <ShoppingCartItem, Integer>{

}
