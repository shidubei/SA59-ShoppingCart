package controller;

import model.Product;
import model.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sevice.AddProductService;
import sevice.AddQuantityService;

import java.util.List;

@Controller
@RequestMapping("addproduct")
public class AddProductController {
    @Autowired
    private AddProductService addProductService;
    @Autowired
    AddQuantityService addQuantityService;

    @GetMapping("/findProducts")
    public String Productlist(Model model){
        model.addAttribute("product",addProductService.findAllProducts());
        return "product-list";
    }
    @GetMapping("/items")
    public String addProduct(Model model){
        ShoppingCartItem shoppingCartItems=new ShoppingCartItem();
        model.addAttribute("shoppingCartItem",shoppingCartItems);
        return "shoppingCartItemList";
    }
    @PostMapping("/addItems")
    public ResponseEntity<String> addProducts(@RequestBody Product product){
        ShoppingCartItem existingItem = addQuantityService.findByProductId(product.getProductId());
        if(existingItem!=null){
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            addQuantityService.add(existingItem); // 更新购物车项
            return ResponseEntity.ok("Product quantity updated successfully!");
        } else {
            // 如果购物车中没有该产品，添加产品
            ShoppingCartItem newItem = addProductService.addProduct(product);
            return ResponseEntity.ok("Product added to cart successfully!");
        }
    }

}
