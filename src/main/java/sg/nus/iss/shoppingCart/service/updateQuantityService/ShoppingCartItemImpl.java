package sg.nus.iss.shoppingCart.service.updateQuantityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import sg.nus.iss.shoppingCart.repository.ShoppingCartItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShoppingCartItemImpl implements ShoppingCartItemService {
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public List<ShoppingCartItem> findAllShoppingCartItem(){
       return shoppingCartItemRepository.findAll();
    }
    @Transactional(readOnly = false)
    @Override
    public ShoppingCartItem updateQuantity(ShoppingCartItem shoppingCartItem){
        ShoppingCartItem existingItem = shoppingCartItemRepository.findById(shoppingCartItem.getId())
                .orElseThrow(() -> new RuntimeException("ShoppingCartItem not found"));

        // Update the quantity
        existingItem.setQuantity(shoppingCartItem.getQuantity());

        // Save and return the updated item
        return shoppingCartItemRepository.save(existingItem);
    }
}
