package sg.nus.iss.shoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.nus.iss.shoppingCart.interfacemethods.CustomerInterfacemethods;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.repository.CustomerRepository;
import sg.nus.iss.shoppingCart.repository.ShoppingCartRepository;

@Service
@Transactional
public class CustomerService implements CustomerInterfacemethods {
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepo;
	
	@Override
	//@Transactional
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}
	
	@Override
	//@Transactional
	public Optional<Customer> findByNameAndPassword(String name, String password) {
		return customerRepo.findByNameAndPassword(name,password);
	}
	
	@Override
	@Transactional
	public void addNew(Customer customer) {
		// add the new customer
		customerRepo.save(customer);
		// make a new shopping cart for the customer
		ShoppingCart newShoppingCart = new ShoppingCart();
		newShoppingCart.setCustomer(customer);
		shoppingCartRepo.save(newShoppingCart);
	}
	
	@Override
	//@Transactional
	public List<Customer> findByName(String name) {
		return customerRepo.findByName(name);
	}
}
