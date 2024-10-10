package sevice;

import model.Product;
import model.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ProductRepository;
import repository.ShoppingCartItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AddProductSeviceImpl implements AddProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Override
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public ShoppingCartItem addProduct(Product product){
      ShoppingCartItem sc=new ShoppingCartItem();
      sc.setId(product.getProductId());
      sc.setQuantity(1);

      return shoppingCartItemRepository.save(sc);
    }

}
