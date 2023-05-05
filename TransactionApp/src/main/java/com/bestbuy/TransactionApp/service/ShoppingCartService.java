package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.CartItemResponse;
import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.model.CartItem;
import com.bestbuy.TransactionApp.model.ShoppingCart;
import com.bestbuy.TransactionApp.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;

    public ShoppingCartResponse createShoppingCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        return mapToShoppingCartResponse(savedShoppingCart);

    }

    private ShoppingCartResponse mapToShoppingCartResponse(ShoppingCart shoppingCart){
        return ShoppingCartResponse.builder()
                .userId(shoppingCart.getUserId())
                .createdAt(shoppingCart.getCreatedAt())
                .cartItemList(shoppingCart.getCartItemList().stream().map(cartItemList -> cartItemService.mapToCartItemResponse(cartItemList)).toList())
                .build();
    }

    public Boolean isShoppingCartEmpty(Long userId){
        return shoppingCartRepository.getReferenceById(userId).getCartItemList().size() == 0;
    }

    public void clearShoppingCart(Long userId){
        shoppingCartRepository.getReferenceById(userId).getCartItemList().clear();
    }

    public CartItemResponse createCartItem(String prodcutId, Integer quantity, Long userId) {
        CartItem cartItem = cartItemService.creatCartItem(prodcutId, quantity);
        ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(userId);
        shoppingCart.getCartItemList().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
        return cartItemService.mapToCartItemResponse(cartItem);
    }

    public List<ShoppingCartResponse> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        return shoppingCarts.stream().map(shoppingCart -> mapToShoppingCartResponse(shoppingCart)).toList();
    }

    public ShoppingCartResponse getShoppingCart(Long userId) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(userId);
        if(optionalShoppingCart.isPresent())
            return mapToShoppingCartResponse(optionalShoppingCart.get());
        else
            throw new NoSuchElementException("Shopping Cart with id " + userId + "doesn't exist");
    }
}
