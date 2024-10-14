package sg.nus.iss.shoppingCart.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.shoppingCart.interfacemethods.AddressInterfacemethods;
import sg.nus.iss.shoppingCart.model.Address;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.service.AddressService;

/**
 * Creator: Zhong Yi
 * Date:7 Oct 2024
 * Explain: this is a controller to handle address request;
 */
@RestController
@RequestMapping("/customer")
public class AddressController {
	@Autowired
	private AddressInterfacemethods addressService;
	
	@Autowired
	private void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	/**
	 * Creator: Zhong Yi
	 * Date:7 Oct 2024
	 * Explain: Handle url /address and list Address
	 */
	@GetMapping("/address")
	public ResponseEntity<List<Address>> listAddress(HttpSession sessionObj,Model model) {
		List<Address> addressList = addressService.listAddressByCutomer((int)sessionObj.getAttribute("customerId"));
		model.addAttribute("addresses",addressList);
		addressList.forEach(address->System.out.println(address));
		return new ResponseEntity<>(addressList,HttpStatus.OK);
	}
	
	@GetMapping("/address/update")
	public ResponseEntity<?> updateAddressPage(HttpSession session, @RequestParam int id) {
		if(addressService.findById(id)!=null) {
			session.setAttribute("updateAddressId", id);
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// This is to update Address
	@PutMapping("/address/update")
	public ResponseEntity<?> updateAddress(HttpSession session,
			@RequestBody Map<String,String> requestBody) {
		String updateAddress = requestBody.get("address");
		addressService.updateAddress((int)session.getAttribute("updateAddressId"),updateAddress);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/*
	 * Creator: Zhong Yi
	 * Date:7 Oct 2024
	 * Explain: Handle url /address/add to display the form to post address
	 */
//	@GetMapping("/address/add")
//	public String addAddressPage() {
//		return "add-address-form";
//	}
	/*
	 * Creator: ZhongYi
	 * Date:7 Oct 2024
	 * Explain: Handle url /address/add to post the address
	 */
	@PostMapping("/address/add")
	public ResponseEntity<Address> addAddress(HttpSession sessionObj, @RequestBody Map<String,String> requestBody) {
		Customer customer = (Customer)sessionObj.getAttribute("customer");
		System.out.println(customer);
		if(customer==null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		Address newAddress = new Address();
		newAddress.setCustomer(customer);
		newAddress.setPre_address(requestBody.get("address"));
		addressService.addNewAddress(newAddress);
		return new ResponseEntity<>(newAddress,HttpStatus.OK);
	}
	
	@DeleteMapping("/address/delete")
	public ResponseEntity<?> deleteAddress(HttpSession session,@RequestParam("id") int id) {
		addressService.deleteAddress(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
