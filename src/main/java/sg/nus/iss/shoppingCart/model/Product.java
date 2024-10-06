package sg.nus.iss.shoppingCart.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private double price;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
	private List<ShoppingCartItem> shoppingCartItems;
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy="orderProduct")
//	private List<OrderDetails> productInOrder;
	
	public Product() {}
	
//	public Product() {
//		this.shoppingCartItems = new ArrayList<>();
//		this.productInOrder = new ArrayList<>();
//	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<ShoppingCartItem> getShoppingCartItems() {
		return shoppingCartItems;
	}

	public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}
	
	
}
