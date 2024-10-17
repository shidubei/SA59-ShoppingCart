package sg.nus.iss.shoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.interfacemethods.CustomerInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.UpdateDetails;
import sg.nus.iss.shoppingCart.model.dto.SignUp;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;
import sg.nus.iss.shoppingCart.repository.ShoppingCartRepository;

/**
 * Creator: Mark
 * Explain: This is the Server Layer Implement for Customer Service
 */
@Service
@Transactional
public class CustomerService implements CustomerInterfacemethods {
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepo;
	
	@Override
//	@Transactional
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}
	
	@Override
//	@Transactional
	public Optional<Customer> findByNameAndPassword(String name, String password) {
		return customerRepo.findByNameAndPassword(name,password);
	}
	
	@Override
	public Optional<Customer> findById(int id) {
		return customerRepo.findById(id);
	}
	
	@Override
	@Transactional
	public Customer updateCustomer(UpdateDetails updateDetails) {
		// get the current account
		int customerId = updateDetails.getCustomerId();
		Optional<Customer> currentCustomerOptional = this.findById(customerId);
		Customer currentCustomer = currentCustomerOptional.get();
		// update email
		currentCustomer.setEmail(updateDetails.getEmail());
		// update name
		currentCustomer.setName(updateDetails.getName());
		// update contact number
		currentCustomer.setContactNumber(updateDetails.getContactNumber());
		// update password if it is changed
		customerRepo.save(currentCustomer);
		return currentCustomer;
	}
	
	@Override
	@Transactional
	public void addNew(SignUp signUp) {
		Customer newCustomer = new Customer();
		newCustomer.setName(signUp.getUsername());
		newCustomer.setEmail(signUp.getEmail());
		newCustomer.setContactNumber(signUp.getContactNumber());
		newCustomer.setPassword(signUp.getPassword1());
		// add the new customer
		customerRepo.save(newCustomer);
		// make a new shopping cart for the customer
		ShoppingCart newShoppingCart = new ShoppingCart();
		newShoppingCart.setCustomer(newCustomer);
		shoppingCartRepo.save(newShoppingCart);
	}
	
	@Override
	@Transactional
	public List<Customer> findByName(String name) {
		return customerRepo.findByName(name);
	}
}
