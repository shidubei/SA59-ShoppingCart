package sg.nus.iss.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.nus.iss.shoppingCart.interfacemethods.ShoppingCartInterface;
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
		ShoppingCart sc = scService.getShoppingCart((int) sessionObj.getAttribute("customerId"));
		List<ShoppingCartItem> items = sc.getShoppingCartItems();
		for (ShoppingCartItem s : items) {
			System.out.printf("Product: %s, quantity: %d%n", s.getProduct().getName(), s.getQuantity());
		}
		if (items.isEmpty() != true) {
			model.addAttribute("isEmpty", false);
			model.addAttribute("items", items);
		} else {
			model.addAttribute("isEmpty", true);
		}

		return "shopping-cart-page";
	}

	@PutMapping("/shopping-cart-page/updateQuantity")
	public String updateQuantity(@RequestParam("id") int id,
								 @RequestParam("quantity") int quantity,
								 RedirectAttributes redirectAttributes,
								 ShoppingCartItem shoppingCartItem,
								 HttpSession sessionObj) {
		try {
			List<ShoppingCartItem> shoppingCartItem1 = (List<ShoppingCartItem>) sessionObj.getAttribute("shoppingCartItems");
			if (shoppingCartItem1 != null) {

				sessionObj.getAttribute("quantity");
				scService.updateQuantity(shoppingCartItem);
				sessionObj.setAttribute("quantity", quantity);
				redirectAttributes.addFlashAttribute("success", "Quantity updated successfully!");
			} else {
				redirectAttributes.addFlashAttribute("error", "Item not found!");
			}
		} catch (RuntimeException e) {

			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		// Redirect back to the items list
		return "redirect:/shoppingCart";
	}
	@DeleteMapping("/shopping-cart-page/delete/{id}")
	public String deleteItem(@PathVariable("id") int id , HttpSession sessionObj) {
		List<ShoppingCartItem> shoppingCartItem1 = (List<ShoppingCartItem>) sessionObj.getAttribute("shoppingCartItems");
		if(shoppingCartItem1!=null){ scService.deleteProduct(id);
			sessionObj.setAttribute("deletedlist", shoppingCartItem1);}
		return "shopping-cart-page";
	}
}
