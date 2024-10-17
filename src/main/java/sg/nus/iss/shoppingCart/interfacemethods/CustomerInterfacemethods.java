package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.UpdateDetails;
import sg.nus.iss.shoppingCart.model.dto.SignUp;

public interface CustomerInterfacemethods {

	public List<Customer> getAllCustomers();
	
	public Optional<Customer> findByNameAndPassword(String name, String password);
	
	public void addNew(SignUp signUp);
	
	public Customer updateCustomer(UpdateDetails updateDetails);
	
	public List<Customer> findByName(String name);
	
	public Optional<Customer> findById(int id);
}
