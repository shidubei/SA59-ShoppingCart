package sg.nus.iss.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.service.ProductService;


@Controller
public class ProductController {
	@Autowired
	private ProductInterfacemethods productService;
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService=productService;
	}
	
	/**
	 * Creator: ZhongYi
	 * Date:5 Oct 2024
	 * Explain: this handler is to list Product
	 */
	@GetMapping("/products")
	public String listProducts(Model model) {
		List<Product> productsList =productService.listProducts();
		model.addAttribute("productsList",productsList);
		return "list-product";
	}
}
