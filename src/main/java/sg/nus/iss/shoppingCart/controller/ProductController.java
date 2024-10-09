
package sg.nus.iss.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.service.ProductService;

import java.util.List;

@Controller
public class ProductController {
	
	// change:
	// 1.use interface and service to use server
    @Autowired
    private ProductInterfacemethods productService;
    
    @Autowired
    private void setProductService(ProductService productService) {
    	this.productService=productService;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productService.listProducts();
        model.addAttribute("products", products);
        return "productList"; // 返回视图名称
    }
}

