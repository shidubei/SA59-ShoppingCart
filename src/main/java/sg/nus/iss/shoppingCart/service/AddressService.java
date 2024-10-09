package sg.nus.iss.shoppingCart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sg.nus.iss.shoppingCart.interfacemethods.AddressInterfacemethods;

import sg.nus.iss.shoppingCart.model.Address;
import sg.nus.iss.shoppingCart.repository.AddressRepository;

/**
 * Creator: Zhong Yi
 * Date:7 Oct 2024
 * Explain: This is the Server Layer Implement for Address Service
 */

@Service
public class AddressService implements AddressInterfacemethods {
	@Autowired
	AddressRepository addressRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Address> listAddressByCutomer(int customerId) {
		return addressRepo.findByCustomerId(customerId);
	}

	@Override
	public void addNewAddress(Address newAddress) {
		addressRepo.save(newAddress);
	}

	@Override
	@Transactional
	public void updateAddress(int id,String pre_address) {
		Address address=entityManager.find(Address.class, id);
		if(address!=null) {
			address.setPre_address(pre_address);
		}else {
			throw new RuntimeException("Address not found for id");
		}
	}

	@Override
	public void deleteAddress(int id) {
		addressRepo.deleteById(id);	
	}

}
