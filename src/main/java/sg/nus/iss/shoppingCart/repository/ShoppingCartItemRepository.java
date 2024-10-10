package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemRepository extends JpaRepository <ShoppingCartItem, Integer>{
    List<ShoppingCartItem> findByProductId(int id);
}
