package sg.nus.iss.shoppingCart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public List<Address> listAddressByCutomer(int customerId) {
		return addressRepo.findByCustomerId(customerId);
	}

	@Override
	public void addNewAddress(Address newAddress) {
		addressRepo.save(newAddress);
	}

	@Override
	public void updateAddress(String pre_address) {
		// TODO Auto-generated method stub
		
	}
	
}
