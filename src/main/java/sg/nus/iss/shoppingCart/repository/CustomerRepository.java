package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	 // find by name; needed to prevent duplicate accounts
	public List<Customer> findByName(String name);
	
	// find by name and password; needed to login
	public Customer findByNameAndPassword(String name, String password);
	
	// find by id; general retrieve data
	//public Customer findById(int id);
	
}
