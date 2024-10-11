package sg.nus.iss.shoppingCart.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sg.nus.iss.shoppingCart.model.Product;
import sg.nus.iss.shoppingCart.repository.AdminProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AdminProductValidator implements Validator {

    @Autowired
    private AdminProductRepository adminProductRepository;

    // Check if the object is a Product
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    // Validate the Product object
    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        // Check if name is empty
        if (product.getName().isEmpty()) {
            errors.rejectValue("name", "product.name.empty");
        }

        // Check if price is less than 0
        if (product.getPrice().compareTo(BigDecimal.valueOf(0.0)) < 0) {
            errors.rejectValue("price", "product.price.negative");
        }

        // Check if category is empty
        if (product.getCategory().isEmpty()) {
            errors.rejectValue("category", "product.category.empty");
        }

        // Check if name is unique
        String name = product.getName();
        List<Product> sameName = adminProductRepository.findByName(name);
        if (!sameName.isEmpty()) {
            errors.rejectValue("name", "product.name.duplicate");
        }
    }
}
