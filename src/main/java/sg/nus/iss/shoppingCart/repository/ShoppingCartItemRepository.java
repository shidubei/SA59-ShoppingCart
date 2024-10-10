package repository;

import model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Integer> {
    ShoppingCartItem findByProductId(int productId);
}
