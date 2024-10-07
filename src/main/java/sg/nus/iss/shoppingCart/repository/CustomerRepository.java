package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import sg.nus.iss.shoppingCart.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	 // find by name; needed to prevent duplicate accounts
	public List<Customer> findByName(String name);
	
	// find by name and password; needed to login
	public Optional<Customer> findByNameAndPassword(String name, String password);
	
	// find by id; general retrieve data
	public Optional<Customer> findById(int id);
	
}
