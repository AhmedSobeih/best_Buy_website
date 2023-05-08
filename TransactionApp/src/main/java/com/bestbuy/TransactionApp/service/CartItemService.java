package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.CartItemResponse;
import com.bestbuy.TransactionApp.model.CartItem;
import com.bestbuy.TransactionApp.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final StockService stockService;
    public CartItem creatCartItem(String productId,Integer quantity){
        if(!stockService.canDecrementStock(productId,quantity).isPresent()){
            return null;
        }
        stockService.decrementStock(productId,quantity);
        CartItem cartItem = CartItem.builder().quantity(quantity).productId(productId).build();
        return cartItemRepository.save(cartItem);
    }

    public CartItemResponse mapToCartItemResponse(CartItem cartItem){
        return CartItemResponse.builder()
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .id(cartItem.getId())
                .build();
    }
}
