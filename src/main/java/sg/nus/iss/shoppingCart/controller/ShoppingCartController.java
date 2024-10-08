package sg.nus.iss.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;import sg.nus.iss.shoppingCart.interfacemethods.ShoppingCartInterface;
import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import sg.nus.iss.shoppingCart.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
	@Autowired
	private ShoppingCartInterface scService;
	
	@Autowired
	public void setShoppingCartService(ShoppingCartService scService) {
		this.scService = scService;
	}
	
	/*
	 * Creator: Kelly
	 * Date: 05-10-2024
	 * Explain: Get user's shopping cart and display it in HTML
	 */
	@GetMapping("/shoppingCart")
	public String getShoppingCartPage(HttpSession sessionObj, Model model) {
		ShoppingCart sc = scService.getShoppingCart((int)sessionObj.getAttribute("customerId"));
		List<ShoppingCartItem> items = sc.getShoppingCartItems();
		for(ShoppingCartItem s:items) {
			System.out.printf("Product: %s, quantity: %d%n",s.getProduct().getName(),s.getQuantity());
		}
		if(items.isEmpty()!=true) {
			model.addAttribute("isEmpty",false);
			model.addAttribute("items", items);
		}
		else {
			model.addAttribute("isEmpty", true);
		}
		
		return "shopping-cart-page";
	}
	

}
