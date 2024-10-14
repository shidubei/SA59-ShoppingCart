package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;

public interface ShoppingCartItemRepository extends JpaRepository <ShoppingCartItem, Integer>{
	@Query("select s from ShoppingCartItem s where s.product.id=:product_id and s.shoppingCart.id = :shopping_cart_id")
	public ShoppingCartItem findByProductId(@Param("product_id")int product_id,@Param("shopping_cart_id") int shopping_cart_id);
	List<ShoppingCartItem> findByProductId(int id);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM ShoppingCartItem s WHERE s.product.id = :product_id and s.shoppingCart.id=:id")
    void deleteByCategory(@Param("product_id") int product_id, @Param("id") int id);
	
	@Query("select count(sci) from ShoppingCartItem sci where sci.shoppingCart.id = :id")
	public int countItem(@Param("id") int id);
}
