package sg.nus.iss.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.nus.iss.shoppingCart.model.Product;

import java.util.List;
import java.util.Optional;


// The purpose of this is to introduce built-in methods that can be used to interact with the database.
// This is an interface that extends JpaRepository, which is a JPA specific extension of Repository.
// It provides methods like save, findOne, findAll, count, delete, etc.
// The interface is typed to the domain class and the id type of the domain class.
// This interface will allow you to perform various CRUD operations on Product entity.

public interface AdminProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByName(String name);

    List<Product> findAll();

    Optional<Product> deleteById(int id);

    Optional<Product> findById(int id);

}
