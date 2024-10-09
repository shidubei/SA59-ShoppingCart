package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;
import java.util.Optional;

import sg.nus.iss.shoppingCart.model.Customer;

public interface CustomerInterfacemethods {

	public List<Customer> getAllCustomers();
	
	public Optional<Customer> findByNameAndPassword(String name, String password);
	
	public void addNew(Customer customer);
	
	public List<Customer> findByName(String name);
}
