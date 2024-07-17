package com.ecommerce.server.service;

import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.CartItem;
import com.ecommerce.server.model.Product;
import com.ecommerce.server.model.ShoppingCart;
import com.ecommerce.server.model.User;
import com.ecommerce.server.repository.CartItemRepository;
import com.ecommerce.server.repository.ProductRepository;
import com.ecommerce.server.repository.ShoppingCartRepository;
import com.ecommerce.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository lineItemRepository;

    public List<ShoppingCart> getShoppingCarts(){
        return shoppingCartRepository.findAll();
    }

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
                    .cartItems(new ArrayList<>())
                    .build();
            shoppingCart = shoppingCartRepository.save(shoppingCart);
            user.setShoppingCart(shoppingCart);
            userRepository.save(user);
        }

        return shoppingCart;
    }


    public void addProductToShoppingCart(Integer productId) {
        ShoppingCart shoppingCart = getUserShoppingCart();
        List<CartItem> cartItems = shoppingCart.getCartItems();

        if(cartItems.isEmpty()){
            Optional<Product> product = productRepository.findById(productId);
            if(product.isPresent() && product.get().getStockQuantity() >= 1){
                CartItem newLineItem = CartItem.builder()
                        .product(product.get())
                        .quantity(1)
                        .shoppingCart(shoppingCart)
                        .build();
                newLineItem = lineItemRepository.save(newLineItem);
                cartItems.add(newLineItem);
                shoppingCart.setCartItems(cartItems);
                shoppingCartRepository.save(shoppingCart);
                return;
            }
        }

        boolean itemFound = false;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (productId.equals(product.getId())) {
                if (cartItem.getQuantity() < product.getStockQuantity()) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                }
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("product with id "+productId+" is not found"));

            if (product.getStockQuantity() > 0) {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(product);
                newCartItem.setQuantity(1);
                newCartItem.setShoppingCart(shoppingCart);
                cartItems.add(newCartItem);
            } else {
                throw new NotFoundException("Product out of stock");
            }
        }

        shoppingCart.setLastModifiedDate(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }

    public void increaseProductQuantity(Integer productId) {
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> lineItems = shoppingCart.getCartItems();

        for (CartItem lineItem : lineItems) {
            Product product = lineItem.getProduct();
            if (productId.equals(product.getId())) {
                if (lineItem.getQuantity() < product.getStockQuantity()) {
                    lineItem.setQuantity(lineItem.getQuantity() + 1);
                }
                break;
            }
        }

        shoppingCart.setLastModifiedDate(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }

    public void removeProductFromShoppingCart(Integer productId) {
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItems = shoppingCart.getCartItems();

        CartItem itemToRemove = null;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (productId.equals(product.getId())) {
                cartItems.remove(cartItem);
                    lineItemRepository.delete(cartItem);
                    break;
            }
        }

        shoppingCart.setLastModifiedDate(LocalDateTime.now());

        shoppingCartRepository.save(shoppingCart);
    }

    public void decreaseProductQuantity(Integer productId) {
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItems = shoppingCart.getCartItems();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (productId.equals(product.getId())) {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }
                break;
            }
        }

        shoppingCart.setLastModifiedDate(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCart);
    }

}
