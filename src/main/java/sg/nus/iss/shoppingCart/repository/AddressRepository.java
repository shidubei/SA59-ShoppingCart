/**
 * 
 */
package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.shoppingCart.model.Address;

/**
 * Creator: Zhong Yi
 * Date:7 Oct 2024
 * Explain: This is DAO for address table
 */
public interface AddressRepository extends JpaRepository<Address,Integer>{
	@Query("select a from Address a where a.customer.id = :customerId")
	public List<Address> findByCustomerId(@Param("customerId") int customerId);
}
