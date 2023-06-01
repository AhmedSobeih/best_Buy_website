package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.CartItemResponse;
import com.bestbuy.TransactionApp.exception.CartItemExceptionSupplier;
import com.bestbuy.TransactionApp.model.CartItem;
import com.bestbuy.TransactionApp.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final StockService stockService;
    private final CartItemExceptionSupplier cartItemExceptionSupplier;

    public CartItem createCartItem(String productId, Integer quantity){
        stockService.canDecrementStockOrThrow(productId,quantity); // throws if no enough stock
        CartItem cartItem = CartItem.builder().quantity(quantity).productId(productId).build();
        return cartItemRepository.save(cartItem);
    }

    public CartItemResponse deleteCartItem(Long cartItemId) {
        CartItem cartItem = getCartItemById(cartItemId);
        cartItemRepository.delete(cartItem);
        return mapToCartItemResponse(cartItem);
    }

    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(cartItemExceptionSupplier.notFound(cartItemId));
    }

    public CartItem updateCartItem(CartItem cartItem) {
        if(cartItem.getQuantity()<=0)
            throw cartItemExceptionSupplier.invalidQuantity(cartItem.getId());
        return cartItemRepository.save(cartItem);
    }

    public CartItemResponse mapToCartItemResponse(CartItem cartItem){
        return CartItemResponse.builder()
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .id(cartItem.getId())
                .build();
    }

    public CartItemResponse getCartItemResponseById(Long cartItemId) {
        return mapToCartItemResponse(getCartItemById(cartItemId));
    }
}
