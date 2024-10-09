package sg.nus.iss.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.interfacemethods.ShoppingCartInterface;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import sg.nus.iss.shoppingCart.service.ProductService;
import sg.nus.iss.shoppingCart.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
	@Autowired
	private ShoppingCartInterface scService;
	
	@Autowired
	public void setShoppingCartService(ShoppingCartService scService) {
		this.scService = scService;
	}
	
	@Autowired 
	private ProductInterfacemethods productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService=productService;
	}
	
	/*
	 * Creator: Kelly
	 * Date: 05-10-2024
	 * Explain: Get user's shopping cart and display it in HTML
	 */
	@GetMapping("/cart")
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
	
	@GetMapping("/cart/add")
	public String addToShoppingCart(HttpSession sessionObj,@RequestParam("id") int product_id) {
		ShoppingCart sc =scService.getShoppingCart((int)sessionObj.getAttribute("customerId"));
		Product p = productService.getProduct(product_id);
		
		if(scService.checkIfExistInShoppingCart(product_id,sc)==null) {
			// means didn't find, need to new ShoppingCartItem
			// new a shoppingCartItem to 
			ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
			
			shoppingCartItem.setProduct(p);
			shoppingCartItem.setQuantity(1);
			shoppingCartItem.setShoppingCart(sc);
			
			scService.saveShoppingCartItem(shoppingCartItem);
		}else {
			ShoppingCartItem sci = scService.checkIfExistInShoppingCart(product_id, sc);
			scService.updateShoppingCartItemQty(sci.getId(), 1);
		}
		// when after deal the add request,redirect to products page
		return "redirect:/products";
	}

}
