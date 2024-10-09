package sg.nus.iss.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@Controller
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
	public String listAddress(HttpSession sessionObj,Model model) {
		List<Address> addressList = addressService.listAddressByCutomer((int)sessionObj.getAttribute("customerId"));
		model.addAttribute("addresses",addressList);
		return "display-address";
	}
	
	@GetMapping("/address/update")
	public String updateAddressPage(HttpSession session, @RequestParam int id) {
		session.setAttribute("updateAddressId", id);
		return "update-address-form";
	}
	
	@PostMapping("/address/update")
	public String updateAddress(HttpSession session,
			@RequestParam("updateAddress") String address) {
		addressService.updateAddress((int)session.getAttribute("updateAddressId"), address);
		return "redirect:/customer/address";
	}
	
	/*
	 * Creator: Zhong Yi
	 * Date:7 Oct 2024
	 * Explain: Handle url /address/add to display the form to post address
	 */
	@GetMapping("/address/add")
	public String addAddressPage() {
		return "add-address-form";
	}
	/*
	 * Creator: ZhongYi
	 * Date:7 Oct 2024
	 * Explain: Handle url /address/add to post the address
	 */
	@PostMapping("/address/add")
	public String addAddress(HttpSession sessionObj, @RequestParam("address") String address) {
		Customer customer = (Customer)sessionObj.getAttribute("customer");
		if(customer==null) {
			throw new IllegalArgumentException("Invalid argument provided");
		}
		Address newAddress = new Address();
		newAddress.setCustomer(customer);
		newAddress.setPre_address(address);
		addressService.addNewAddress(newAddress);
		return "redirect:/customer/address";
	}
	
	@GetMapping("/address/delete")
	public String deleteAddress(HttpSession session,@RequestParam int id) {
		addressService.deleteAddress(id);
		return "redirect:/customer/address";
	}
	
}
