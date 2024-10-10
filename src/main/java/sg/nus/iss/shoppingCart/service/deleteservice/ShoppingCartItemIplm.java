package sg.nus.iss.javaspring.deleteproduct.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.iss.javaspring.deleteproduct.model.ShoppingCartItem;
import sg.nus.iss.javaspring.deleteproduct.repository.ShoppingCartItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShoppingCartItemIplm implements ShoppingCartItemService {
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Override
    public List<ShoppingCartItem> findAllShoppingCartItem(){
        return shoppingCartItemRepository.findAll();
    }
    @Override
    public void deleteProduct(int id) {
        ShoppingCartItem item = shoppingCartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ShoppingCartItem not found"));

        shoppingCartItemRepository.delete(item);
    }

}
