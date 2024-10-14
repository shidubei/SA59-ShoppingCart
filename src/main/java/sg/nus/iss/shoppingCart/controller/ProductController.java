//编写控制器
package sg.nus.iss.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.service.ProductService;

import java.util.List;

@RestController
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
    public ResponseEntity<List<Product>> listProducts(Model model) {
        List<Product> products = productService.listProducts();
        model.addAttribute("products", products);
        return new ResponseEntity<>(products,HttpStatus.OK); // 返回视图名称
    }
}