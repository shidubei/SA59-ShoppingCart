package sg.nus.iss.shoppingCart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Creator: Zhong Yi
 * Date:7 Oct 2024
 * Explain: This is a table to store the customer's pre-address
 */

@Entity
@Table(name="address")
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@Column(name="pre_address")
	private String pre_address;
	
	public Address() {}
	

	public Address(Customer customer, String pre_address) {
		this.customer = customer;
		this.pre_address = pre_address;
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

	public String getPre_address() {
		return pre_address;
	}

	public void setPre_address(String pre_address) {
		this.pre_address = pre_address;
	}
	
	
}
