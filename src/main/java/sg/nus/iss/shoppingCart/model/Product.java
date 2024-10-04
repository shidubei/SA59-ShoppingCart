package sg.nus.iss.shoppingCart.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table (name="products")
public class Product {
	   	@Id
	    private Long id;

	    private String name;
	    private Double price;

	    @OneToMany(mappedBy = "product")
	    private List<ShoppingCart> shoppingCarts;

}
