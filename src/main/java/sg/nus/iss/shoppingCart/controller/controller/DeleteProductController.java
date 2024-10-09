package sg.nus.iss.shoppingCart.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sg.nus.iss.shoppingCart.service.ShoppingCartItemService;


@Controller
@RequestMapping("/deleteProducts")
public class DeleteProductController {
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    @GetMapping("/find")
    public String ItemsList(Model model){
        model.addAttribute("items",shoppingCartItemService.findAllShoppingCartItem());
        return "ItemsList";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") int id ,Model model){
        shoppingCartItemService.deleteProduct(id);
        model.addAttribute("items",shoppingCartItemService.findAllShoppingCartItem());
        return "redirect:/deleteProducts/find";
    }
}
