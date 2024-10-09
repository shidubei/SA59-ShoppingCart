/**
 * 
 */
package sg.nus.iss.shoppingCart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.shoppingCart.model.Address;

/**
 * Creator: Zhong Yi
 * Date:7 Oct 2024
 * Explain: This is DAO for address table
 */
// 直接对数据进行操作的数据访问层 DAO
public interface AddressRepository extends JpaRepository<Address,Integer>{
	@Query("select a from Address a where a.customer.id = :customerId")
	public List<Address> findByCustomerId(@Param("customerId") int customerId);
	
	@Query("select a from Address a where a.id = :id")
	public Address findById(@Param("id") int id);
	
	@Modifying
	@Transactional
	@Query("delete from Address a where a.id=:id")
	public void deleteById(@Param("id") int id);
	
//	@Query("update Address a set a.pre_address=:pre_address where a.id=:id")
//	public void updateById(@Param("id") int id,@Param("pre_address") String pre_address);
}

