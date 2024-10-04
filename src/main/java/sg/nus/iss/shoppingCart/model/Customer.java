package sg.nus.iss.shoppingCart.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name="customers")
public class Customer {
		@Id
	    private int customerId;

	    @OneToMany(mappedBy = "customer")
	    private List<Order> orders;

}
