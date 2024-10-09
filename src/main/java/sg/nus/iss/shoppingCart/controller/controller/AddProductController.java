package sg.nus.iss.shoppingCart.controller.controller;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sg.nus.iss.shoppingCart.service.AddProductService;
import sg.nus.iss.shoppingCart.service.AddQuantityService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("addproduct")
public class AddProductController {
    private static final String SHOPPING_CART_ITEMS = "shoppingCartItems";

    @Autowired
    private AddProductService addProductService;

    @Autowired
    private AddQuantityService addQuantityService;

    @GetMapping("/findProducts")
    public String productList(Model model) {
        model.addAttribute("product", addProductService.findAllProducts());
        return "product-list";
    }

    @GetMapping("/items")
    public String addProduct(Model model, HttpSession sessionObj) {
        List<ShoppingCartItem> scItems = (List<ShoppingCartItem>) sessionObj.getAttribute(SHOPPING_CART_ITEMS);
        model.addAttribute("shoppingCartItems", scItems != null ? scItems : new ArrayList<>());
        return "shoppingCartItemList";
    }

    @PostMapping("/addItems")
    public ResponseEntity<String> addProducts(@RequestBody Product product, HttpSession sessionObj) {
        List<ShoppingCartItem> shoppingCartItems = (List<ShoppingCartItem>) sessionObj.getAttribute(SHOPPING_CART_ITEMS);
        if (shoppingCartItems == null) {
            shoppingCartItems = new ArrayList<>();
        }

        ShoppingCartItem existingItem = addQuantityService.findByProductId(product.getId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            addQuantityService.add(existingItem); // 更新购物车项
        } else {
            // 如果购物车中没有该产品，添加新商品
            ShoppingCartItem newItem = addProductService.addProduct(product);
            shoppingCartItems.add(newItem); // 将新项添加到购物车列表
        }

        sessionObj.setAttribute(SHOPPING_CART_ITEMS, shoppingCartItems);
        return ResponseEntity.ok("Product added to cart successfully!");
    }
}
