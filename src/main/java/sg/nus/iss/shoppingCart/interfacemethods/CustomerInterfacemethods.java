package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;
import java.util.Optional;

import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.SignUp;
import sg.nus.iss.shoppingCart.model.UpdateDetails;

public interface CustomerInterfacemethods {

	public List<Customer> getAllCustomers();
	
	public Optional<Customer> findByNameAndPassword(String name, String password);
	
	public void addNew(SignUp signUp);
	
	public void updateCustomer(UpdateDetails updateDetails);
	
	public List<Customer> findByName(String name);
	
	public Optional<Customer> findById(int id);
}
