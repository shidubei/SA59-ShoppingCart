
package sg.nus.iss.shoppingCart.repository;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Integer> {
    ShoppingCartItem findByProductId(int productId);
}
