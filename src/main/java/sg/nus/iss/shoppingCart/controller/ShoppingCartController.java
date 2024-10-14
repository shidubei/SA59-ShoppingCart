package sg.nus.iss.shoppingCart.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import sg.nus.iss.shoppingCart.interfacemethods.OrderInterfacemethods;
import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.interfacemethods.ShoppingCartInterface;
import sg.nus.iss.shoppingCart.model.Customer;
import sg.nus.iss.shoppingCart.model.Order;
import sg.nus.iss.shoppingCart.model.OrderDetails;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.model.ShoppingCart;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import sg.nus.iss.shoppingCart.service.OrderService;
import sg.nus.iss.shoppingCart.service.ProductService;
import sg.nus.iss.shoppingCart.service.ShoppingCartService;

@RestController
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
	@Autowired
	private OrderInterfacemethods orderService;
	@Autowired
	public void setOrderService(OrderService orderService) {
		
	}
	/*
	 * Creator: Kelly
	 * Date: 05-10-2024
	 * Explain: Get user's shopping cart and display it in HTML
	 */
	@GetMapping("/shoppingCart")
	public ResponseEntity<List<ShoppingCartItem>> getShoppingCartPage(HttpSession sessionObj, Model model) {
		System.out.println(sessionObj.getAttribute("customerId"));
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
		
		return new ResponseEntity<>(items,HttpStatus.OK);
	}
	
	@PutMapping("/shoppingCart/update")
	public ResponseEntity<?> updateQuantity(@RequestBody Map<String,Integer> requestBody,
								 RedirectAttributes redirectAttributes,
								 ShoppingCartItem shoppingCartItem,
								 HttpSession sessionObj) {
		int product_id = requestBody.get("product_id");
		int quantity = requestBody.get("quantity");
		ShoppingCart sc =scService.getShoppingCart((int)sessionObj.getAttribute("customerId"));
		Product p = productService.getProduct(product_id);
		try {
			if(scService.checkIfExistInShoppingCart(product_id,sc)!=null) {
				ShoppingCartItem sci = scService.checkIfExistInShoppingCart(product_id, sc);
				scService.updateShoppingCartItemQty(sci.getId(), quantity);
			}
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		// Redirect back to the items list
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/shoppingCart/delete")
	public ResponseEntity<?> deleteItem(@RequestParam("id") int product_id , HttpSession sessionObj) {
		ShoppingCart sc =scService.getShoppingCart((int)sessionObj.getAttribute("customerId"));
		try {
			if(scService.checkIfExistInShoppingCart(product_id,sc)!=null) {
				scService.deleteProduct(product_id, sc.getId());
			}
		} catch (RuntimeException e) {
			System.out.println(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/shoppingCart/add")
	public ResponseEntity<?> addToShoppingCart(HttpSession sessionObj,@RequestParam("id") int product_id) {
		System.out.println(sessionObj.getAttribute("customerId"));
		System.out.println(product_id);
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
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/shoppingCart/checkout")
	public ResponseEntity<?> CheckoutAllInShoppingCart(HttpSession sessionObj,@RequestBody Map<String,Object> requestBody) {
		LocalDate orderDate =LocalDate.parse((String)requestBody.get("order_date"), DateTimeFormatter.ISO_LOCAL_DATE);  
	
		Object totalPriceObj = requestBody.get("totalPrice");
		BigDecimal totalPrice;
		if (totalPriceObj instanceof Integer) {
		    totalPrice = BigDecimal.valueOf((Integer) totalPriceObj);
		} else if (totalPriceObj instanceof Double) {
		    totalPrice = BigDecimal.valueOf((Double) totalPriceObj);
		} else {
		    throw new IllegalArgumentException("Unexpected type for price");
		}
		
		List<Map<String, Object>> products = (List<Map<String, Object>>) requestBody.get("products");
		
		Order order = new Order();
		Customer customer = (Customer)sessionObj.getAttribute("customer");
		order.setCustomer(customer);
		order.setOrderDate(orderDate);
		order.setStatus("waiting pending");
		order.setTotalAmount(totalPrice);
		
	
		
		for (Map<String,Object> item:products) {
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setOrder(order);
			Product product = new Product();
			// check if price is Integer or Double
			Object priceObj = item.get("price");
			BigDecimal price;
			if (priceObj instanceof Integer) {
			    price = BigDecimal.valueOf((Integer) priceObj);
			} else if (priceObj instanceof Double) {
			    price = BigDecimal.valueOf((Double) priceObj);
			} else {
			    throw new IllegalArgumentException("Unexpected type for price");
			}
			
			int id = (int)item.get("id");
			int quantity = (int) item.get("quantity");
			product.setId(id);
			product.setName((String)item.get("name"));
			product.setPrice(price);
			product.setCategory((String)item.get("category"));
			orderDetails.setProduct(product);
			orderDetails.setUnits(price.multiply(BigDecimal.valueOf(quantity)));
			orderService.CreateOrder(order, orderDetails);
			ShoppingCart sc =scService.getShoppingCart((int)sessionObj.getAttribute("customerId"));
			try {
				if(scService.checkIfExistInShoppingCart(id,sc)!=null) {
					scService.deleteProduct(id, sc.getId());
				}
			} catch (RuntimeException e) {
				System.out.println(e);
			}
		}
		
		
		
		// when after deal the add request,redirect to products page
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/shoppingCart/count")
	public ResponseEntity<Integer> getCount(HttpSession sessionObj){
		ShoppingCart sc =scService.getShoppingCart((int)sessionObj.getAttribute("customerId"));
		int count = scService.countItem(sc.getId());
		return new ResponseEntity<>(count,HttpStatus.OK);
	}
}
