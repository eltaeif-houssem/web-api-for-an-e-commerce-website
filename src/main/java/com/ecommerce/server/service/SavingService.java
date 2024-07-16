package com.ecommerce.server.service;

import com.ecommerce.server.model.Product;
import com.ecommerce.server.model.User;
import com.ecommerce.server.repository.ProductRepository;
import com.ecommerce.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public List<Product> getUserSavings(){
        User user = userService.getCurrentUser();
        return user.getSavings();
    }

    public void addProductToSavings(Integer productId){
        User user = userService.getCurrentUser();
        List<Product> savings = user.getSavings();

        for(Product product:savings){
            if(product.getId().equals(productId)){
                return;
            }
        }

        Product product = productService.getProduct(productId);
        savings.add(product);
        user.setSavings(savings);

        userRepository.save(user);
    }

    public void removeProductFromSavings(Integer productId){
        User user = userService.getCurrentUser();
        List<Product> savings = user.getSavings();

        for(Product product:savings){
            if(product.getId().equals(productId)){
                savings.remove(product);
                user.setSavings(savings);
                userRepository.save(user);
                return;
            }
        }
    }
}
