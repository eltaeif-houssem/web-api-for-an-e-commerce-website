package com.ecommerce.server.service;

import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.ShoppingCart;
import com.ecommerce.server.model.User;
import com.ecommerce.server.repository.ShoppingCartRepository;
import com.ecommerce.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ShoppingCart getShoppingCartById(Integer id){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(id);
        if(shoppingCart.isEmpty()){
            throw new NotFoundException("shopping cart with the id "+id+" was not found");
        }
        return shoppingCart.get();
    }

    public ShoppingCart getUserShoppingCart(){
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();

        if(shoppingCart == null){
            ShoppingCart newShoppingCart = ShoppingCart.builder()
                    .user(user)
                    .createdDate(LocalDateTime.now())
                    .build();
            user.setShoppingCart(newShoppingCart);
            userRepository.save(user);
            return newShoppingCart;
        }

        return shoppingCart;
    }
}
