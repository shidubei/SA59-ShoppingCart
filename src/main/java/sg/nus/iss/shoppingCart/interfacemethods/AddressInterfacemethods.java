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
	
	/**
	 * Creator: Zhong Yi
	 * Date:7 Oct 2024
	 * Explain: This mehtod will be used when customer want to update their pre-address
	 */
	public void updateAddress(int id,String pre_address);
	
	/**
	 * Creator: Zhong Yi
	 * Date:7 Oct 2024
	 * Explain: This method will be used when customer want to delete their pre_address
	 */
	public void deleteAddress(int id);

	/**
	 * Creator:
	 * Date:11 Oct 2024
	 * Explain:
	 */
	Address findById(int id);
	
}
