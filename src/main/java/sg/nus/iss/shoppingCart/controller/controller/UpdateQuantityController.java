package sg.nus.iss.shoppingCart.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import sg.nus.iss.shoppingCart.service.ShoppingCartItemService;

import java.util.List;

@Controller
@RequestMapping("/view")
public class UpdateQuantityController {
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    @GetMapping("/find")
    public String ItemsList(Model model){
        model.addAttribute("items",shoppingCartItemService.findAllShoppingCartItem());
        return "items-list";

    }
    @PutMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("id") int id,
                                 @RequestParam("quantity") int quantity,
                                 RedirectAttributes redirectAttributes) {
        try {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setId(id);
            shoppingCartItem.setQuantity(quantity);


            shoppingCartItemService.updateQuantity(shoppingCartItem);

            // Add success message to redirect attributes
        } catch (RuntimeException e) {
            // Handle the exception and add an error message
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        // Redirect back to the items list
        return "redirect:/view/find";
    }

}
