package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.nus.iss.shoppingCart.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
