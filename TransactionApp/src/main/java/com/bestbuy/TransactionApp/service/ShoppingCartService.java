package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.model.ShoppingCart;
import com.bestbuy.TransactionApp.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartResponse createShoppingCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        return mapToShoppingCartResponse(savedShoppingCart);

    }

    private ShoppingCartResponse mapToShoppingCartResponse(ShoppingCart shoppingCart){
        return ShoppingCartResponse.builder()
                .userId(shoppingCart.getUserId())
                .createdAt(shoppingCart.getCreatedAt()).build();
        //TODO: list of cart items building
    }
}
