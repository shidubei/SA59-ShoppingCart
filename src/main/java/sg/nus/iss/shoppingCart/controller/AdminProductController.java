package sg.nus.iss.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.service.AdminProductService;
import sg.nus.iss.shoppingCart.validation.AdminProductValidator;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AdminProductController {

    // This @Autowired annotation tells Spring to inject an instance of ProductService here
    @Autowired
    private AdminProductService adminProductService;

    // This @Autowired annotation tells Spring to inject an instance of AdminProductValidator here
    @Autowired
    private AdminProductValidator adminProductValidator;

    // This method is called before the createProduct method is called
    // It tells Spring to use the adminProductValidator to validate the product object
    @InitBinder
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
    public String listProducts(Model model) {
        List<Product> products = adminProductService.listProducts();
        model.addAttribute("products", products);
        return "adminProductList";
    }

    // This method is used to create a new product
    @GetMapping("/admin/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "adminProductForm";
    }

    // This method is used to create a new product
    // It uses the @ModelAttribute annotation to bind the product object to the form
    // It uses the @Valid annotation to validate the product object
    // It uses the BindingResult object to check for validation errors
    // If there are validation errors, it returns back to the form
    // If there are no errors, it saves the product and redirects to the product listing page
    @PostMapping("/admin/products")
    public String createProduct(@ModelAttribute("product") Product product, Model model) {
//        if (result.hasErrors()) {
//            // If there are validation errors, return back to the form
//            return "adminProductForm";
//        }
        // If no errors, save the product
        adminProductService.createProduct(product.getName(), product.getPrice(), product.getCategory());
        return "redirect:/admin/products";  // Redirect to the product listing page
    }

    // This method is used to delete a product
    // If there are no errors, it deletes the product and redirects to the product listing page
    @PostMapping("/admin/products/delete")
    public String deleteProduct(@ModelAttribute("product") Product product, Model model) {
//        if (product.getId() == -1) {
//            result.rejectValue("id", "error.product", "Product ID is required for deletion.");
//            return "redirect:/admin/products";  // Redirect back to the product listing if there's an issue
//        }

        try {
            // Attempt to delete the product
            adminProductService.deleteProduct(product.getId());
        } catch (Exception e) {
            // Handle any exceptions that occur during the deletion process
            model.addAttribute("deleteError", "Unable to delete product. Please try again.");
            return "adminProductList";  // Stay on the same page and show an error
        }

        // If deletion is successful, redirect back to the product listing page
        return "redirect:/admin/products";
    }


    // This method is used to update a product
    // If there are no errors, it updates the product and redirects to the product listing page
    @GetMapping("/admin/products/update")
    public String updateProductForm(HttpSession sessionObj,@RequestParam("productId") int id, Model model) {
    	System.out.println(id);
        sessionObj.setAttribute("updateProductId", id);
        return "adminProductForm";
    }
    @PostMapping("/admin/products/update")
    public String updateProduct(HttpSession sessionObj,@ModelAttribute("product") Product product,@RequestParam("product_name") String product_name, @RequestParam("price") BigDecimal price,@RequestParam("category") String category, Model model) {
        // If no errors, save the product
        adminProductService.updateProduct((int)sessionObj.getAttribute("updateProductId"),product_name, price, category);
        return "redirect:/admin/products";  // Redirect to the product listing page
    }

}
