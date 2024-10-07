package sg.nus.iss.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.service.ProductService;
import sg.nus.iss.shoppingCart.service.ShoppingCartService;


@Controller
public class ProductController {
	@Autowired
	private ProductInterfacemethods productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService=productService;
	}
	public void setShoppingCartService(ShoppingCartService shoppingService) {
		
	}
	
	
	/*
	 * Creator: ZhongYi
	 * Date:5 Oct 2024
	 * Explain: this handler is to list Product
	 */
	@GetMapping("/products")
	public String listProducts(Model model) {
		// 调用service里面的方法
		List<Product> productsList =productService.listProducts();
		// 添加到模型里面
		model.addAttribute("productsList",productsList);
		// 返回给Thymeleaf处理
		return "list-product";
	}

}
