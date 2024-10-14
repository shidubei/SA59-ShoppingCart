package sg.nus.iss.shoppingCart.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	// change:
	// 1. order_date in datebase's type is Date, so use Local Date to map the type
	@Column(name="order_date")
	private LocalDate orderDate;
	
	@Column (name = "status", length = 10)
	private String status; 
	
	@Column (name = "total_amount", precision=10,scale=2) 
	private BigDecimal totalAmount; 
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	// change:
	// 2. order_details table have other attribute, so we need a OrderDetails Entity to Map it
	//    so do not use JoinTable to Map
	@OneToMany(fetch=FetchType.EAGER,mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<OrderDetails> orderDetails;
	
	public Order() {}
	
	public Order(int id, LocalDate orderDate, Customer customer,String status,BigDecimal totalAmount) {
		this.id = id;
		this.orderDate = orderDate;
		this.status = status;
		this.totalAmount = totalAmount;
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
