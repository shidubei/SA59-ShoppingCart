package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.iss.shoppingCart.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer
> {

}
