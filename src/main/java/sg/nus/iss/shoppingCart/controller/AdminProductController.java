package sg.nus.iss.shoppingCart.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.shoppingCart.interfacemethods.AdminProductInterfaceMethods;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.service.AdminProductService;
import sg.nus.iss.shoppingCart.validation.AdminProductValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creator: WangZhongYun
 * Explain: The Controller for Admin user to CRUD product
 * RestAPI Regenerator: ZhongYi (change the controller to RestAPI and design ResponseEntity)
 */
@RestController
public class AdminProductController {

    // This @Autowired annotation tells Spring to inject an instance of ProductService here
    @Autowired
    private AdminProductInterfaceMethods adminProductService;

    // This @Autowired annotation tells Spring to inject an instance of AdminProductValidator here
    @Autowired
    private AdminProductValidator adminProductValidator;

    // This method is called before the createProduct method is called
    // It tells Spring to use the adminProductValidator to validate the product object
    @InitBinder("product")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(adminProductValidator);
    }

    // This method is used to set the AdminProductService instance
    // It is used to inject the AdminProductService instance into this class
    @Autowired
    private void setAdminProductService(AdminProductService adminProductService) {
        this.adminProductService=adminProductService;
    }

    // This method is used to set the AdminProductValidator instance
    // It is used to inject the AdminProductValidator instance into this class
    // @GetMapping defines the routable endpoint for HTTP GET requests.
    // The first line of the method is used to get all products from the database.
    // The second line is used to add the products to the model, which are then automatically passed to the web page you stated to route to afterward.
    // The third line is used to return the view name.
    
    @GetMapping("/admin/products")
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = adminProductService.listProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    // This method is used to create a new product
//    @GetMapping("/admin/products/new")
//    public String newProduct(Model model) {
//        model.addAttribute("product", new Product());
//        return "adminProductForm";
//    }

    // This method is used to create a new product
    // It uses the @ModelAttribute annotation to bind the product object to the form
    // It uses the @Valid annotation to validate the product object
    // It uses the BindingResult object to check for validation errors
    // If there are validation errors, it returns back to the form
    // If there are no errors, it saves the product and redirects to the product listing page
    @PostMapping("/admin/products/add")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product,BindingResult bindingResult) {
    	if(bindingResult.hasErrors()) {
    		return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
    	}
        // If no errors, save the product
        adminProductService.createProduct(product.getName(), product.getPrice(), product.getCategory());
        return new ResponseEntity<>(null,HttpStatus.CREATED);  
    }


    @PutMapping("/admin/products/update")
    public ResponseEntity<Object> updateProduct(@RequestBody Map<String,Object> requestBody) {
    	int update_id  = (int)requestBody.get("id");
    	Object priceObj = requestBody.get("price");
    	BigDecimal price;
    	if (priceObj instanceof Integer) {
		   price = BigDecimal.valueOf((Integer) priceObj);
		} else if (priceObj instanceof Double) {
		    price = BigDecimal.valueOf((Double) priceObj);
		} else {
		    throw new IllegalArgumentException("Unexpected type for price");
		}
    	String name = (String)requestBody.get("name");
    	String category = (String)requestBody.get("category");
    	boolean isSelling = (boolean)requestBody.get("isSelling");
        // If no errors, save the product
        adminProductService.updateProduct(update_id,name,price,category,isSelling);
        return new ResponseEntity<>(null,HttpStatus.OK);  // Redirect to the product listing page
    }

}
