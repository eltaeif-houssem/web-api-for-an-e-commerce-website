package com.ecommerce.server.service;

import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.LineItem;
import com.ecommerce.server.model.Product;
import com.ecommerce.server.model.ShoppingCart;
import com.ecommerce.server.model.User;
import com.ecommerce.server.repository.ProductRepository;
import com.ecommerce.server.repository.ShoppingCartRepository;
import com.ecommerce.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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
            shoppingCart = ShoppingCart.builder()
                    .user(user)
                    .createdDate(LocalDateTime.now())
                    .build();
            shoppingCart = shoppingCartRepository.save(shoppingCart);
            user.setShoppingCart(shoppingCart);
            userRepository.save(user);
        }

        return shoppingCart;
    }

    public void addProductToShoppingCart(Integer productId) {
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<LineItem> lineItems = shoppingCart.getLineItems();

        boolean itemFound = false;

        for (LineItem lineItem : lineItems) {
            Product product = lineItem.getProduct();
            if (productId.equals(product.getId())) {
                if (lineItem.getQuantity() < product.getStockQuantity()) {
                    lineItem.setQuantity(lineItem.getQuantity() + 1);
                    lineItem.setPrice(lineItem.getPrice() + product.getPrice());
                }
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("product with id "+productId+" is not found"));

            if (product.getStockQuantity() > 0) {
                LineItem newLineItem = new LineItem();
                newLineItem.setProduct(product);
                newLineItem.setQuantity(1);
                newLineItem.setPrice(product.getPrice());
                newLineItem.setShoppingCart(shoppingCart);
                lineItems.add(newLineItem);
            } else {
                throw new NotFoundException("Product out of stock");
            }
        }

        shoppingCart.setLastModifiedDate(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }


    public void removeProductFromShoppingCart(Integer productId) {
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<LineItem> lineItems = shoppingCart.getLineItems();

        LineItem itemToRemove = null;

        for (LineItem lineItem : lineItems) {
            Product product = lineItem.getProduct();
            if (productId.equals(product.getId())) {
                if (lineItem.getQuantity() > 1) {
                    lineItem.setQuantity(lineItem.getQuantity() - 1);
                    lineItem.setPrice(lineItem.getPrice() - product.getPrice());
                } else {
                    itemToRemove = lineItem;
                }
                break;
            }
        }

        if (itemToRemove != null) {
            lineItems.remove(itemToRemove);
        }

        shoppingCart.setLastModifiedDate(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }
}
