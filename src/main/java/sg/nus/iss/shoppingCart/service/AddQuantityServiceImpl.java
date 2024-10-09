package sg.nus.iss.shoppingCart.service;

import sg.nus.iss.shoppingCart.model.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.iss.shoppingCart.repository.ProductRepository;
import sg.nus.iss.shoppingCart.repository.ShoppingCartItemRepository;

@Service
@Transactional(readOnly = true)
public class AddQuantityServiceImpl implements AddQuantityService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public ShoppingCartItem findByProductId(int id){
        return shoppingCartItemRepository.findByProductId(id);
    }
    @Override
    public ShoppingCartItem add(ShoppingCartItem shoppingCartItem){
        ShoppingCartItem existingItem = shoppingCartItemRepository.findById(shoppingCartItem.getId())
                .orElseThrow(() -> new RuntimeException("ShoppingCartItem not found with id: " + shoppingCartItem.getId()));

        existingItem.setQuantity(existingItem.getQuantity() + 1);

        return shoppingCartItemRepository.save(existingItem);
    }

}
