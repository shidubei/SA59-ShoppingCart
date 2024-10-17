/**
 * 
 */
package sg.nus.iss.shoppingCart.interfacemethods;

import java.util.List;

import sg.nus.iss.shoppingCart.model.Address;

/**
 * Creator: Zhong Yi
 * Date:7 Oct 2024
 * Explain: this is a Server Layer Interface about address
 */
public interface AddressInterfacemethods {

	public List<Address> listAddressByCutomer(int customerId);
	
	public void addNewAddress(Address newAddress);
	

	public void updateAddress(int id,String pre_address);
	
	public void deleteAddress(int id);

	public Address findById(int id);
	
}
