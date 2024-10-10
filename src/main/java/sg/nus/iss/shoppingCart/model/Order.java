package sg.nus.iss.shoppingCart.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name ="ordertable", schema = "shoppingcart")
public class Order {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id; 
	
	@Column (name = "orderDate", length = 35)
	private LocalDate orderDate;
	
	@Column (name = "status", length = 10)
	private String status; 
	
	@Column (name = "totalAmount", length = 10) 
	private double totalAmount; 
	
	@ManyToOne
	@JoinColumn (name = "customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy= "order")
	private List<OrderDetails> orderDetails; 
		
	public Order() {}

	
	public Order(int id, LocalDate orderDate, String status, double totalAmount, Customer customer) {
		this.id = id;
		this.orderDate = orderDate;
		this.status = status;
		this.totalAmount = totalAmount;
		this.customer = customer;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDate=" + orderDate + ", status=" + status + ", totalAmount=" + totalAmount
				+ ", customer=" + customer + "]";
	}
}

/* Creator: Azril
 * Date: 2024-10-10 */

