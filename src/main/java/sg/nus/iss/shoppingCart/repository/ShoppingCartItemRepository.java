package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

public interface ShoppingCartItemRepository extends JpaRepository <ShoppingCartItem, Integer>{
	
	@Query("select s from ShoppingCartItem s where s.product.id=:product_id and s.shoppingCart.id = :shopping_cart_id")
	public ShoppingCartItem findByProductId(@Param("product_id")int product_id,@Param("shopping_cart_id") int shopping_cart_id);
	// 找到的话返回一个ShoppingCartItem类
	// 没找到返回一个null
}
