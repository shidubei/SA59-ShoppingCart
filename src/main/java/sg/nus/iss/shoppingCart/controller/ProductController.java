package sg.nus.iss.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.iss.shoppingCart.interfacemethods.ProductInterfacemethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.service.ProductService;

import java.util.List;

/**
 * Creator: Chingnam
 * Explain: The controller deal product request
 * RestAPI Regenerator: ZhongYi (change the controller to RestAPI and design ResponseEntity)
 */
@RestController
public class ProductController {
	
    @Autowired
    private ProductInterfacemethods productService;
    
    @Autowired
    private void setProductService(ProductService productService) {
    	this.productService=productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> listProducts(Model model) {
        List<Product> products = productService.listSellingProducts();
        model.addAttribute("products", products);
        return new ResponseEntity<>(products,HttpStatus.OK); 
    }
    
    @GetMapping("/products/{category}")
    public ResponseEntity<List<Product>> listProductsCategory(@PathVariable String category){
    	List<Product> products = productService.listProductByCategory(category);
    	return new ResponseEntity<>(products,HttpStatus.OK);
    }
    
    /**
     * Creator: Kelly
     */
    @GetMapping("/products/{name}")
    public ResponseEntity<List<Product>> listProductByName(@PathVariable String name){
    	List<Product> products = productService.listProductByCategory(name);
    	return new ResponseEntity<>(products,HttpStatus.OK);
    }
    
    /**
     * Creator: Kelly
     */
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(required =false) String name,@RequestParam(required=false) String category){
    	List<Product> products = productService.listProdcutByNameAndCategory(name, category);
    	return new ResponseEntity<>(products,HttpStatus.OK);
    }
}