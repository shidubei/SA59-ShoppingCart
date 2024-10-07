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
	/**
	 * Creator: Zhong Yi
	 * Date:7 Oct 2024
	 * Explain: This method will be used when customer want to view their pre_address
	 * Parameter: 
	 *   1.customerId: can be get By customer.getId() to show the customer's pre-addresses
	 */
	public List<Address> listAddressByCutomer(int customerId);
	
	/**
	 * Creator: Zhong Yi
	 * Date:7 Oct 2024
	 * Explain: This method will be used when customer want to add a new pre_address
	 * Parameter:
	 *  1.newAddress: need a Address Object to create in table
	 */
	public void addNewAddress(Address newAddress);
	
	public void updateAddress(String pre_address);
}
