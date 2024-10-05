package sg.nus.iss.shoppingCart.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="shoppingcart")
public class ShoppingCart {
	
	@Id
	private int id;
	
	@OneToOne
	@JoinColumn(name="customerid")
	private Customer customer;
	
	@ManyToMany
	@JoinTable(
		name="shoppingcartitem",
		joinColumns=@JoinColumn(name="shoppingcart_id"),
		inverseJoinColumns=@JoinColumn(name="product_id")
	)
	private List<Product> products;
	
	
	public ShoppingCart() {}


	public ShoppingCart(int id, Customer customer, List<Product> products) {
		this.id = id;
		this.customer = customer;
		this.products = products;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public List<Product> getProducts() {
		return products;
	}


	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
