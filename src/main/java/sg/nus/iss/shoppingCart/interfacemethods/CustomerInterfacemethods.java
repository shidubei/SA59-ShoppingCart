package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.shoppingCart.model.Customer;

public interface CustomerInterfacemethods {

	public List<Customer> getAllCustomers();
	
	public Optional<Customer> findByNameAndPassword(String name, String password);
	
	public void addNew(Customer customer);
	
	public List<Customer> findByName(String name);
	
	//Added find by customer by Id - Azril
	public Customer findCustomerById(int customerId);

}
