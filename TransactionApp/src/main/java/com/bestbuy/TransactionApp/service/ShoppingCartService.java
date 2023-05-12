package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.CartItemResponse;
import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.exception.CartExceptionSupplier;
import com.bestbuy.TransactionApp.exception.CartItemExceptionSupplier;
import com.bestbuy.TransactionApp.model.CartItem;
import com.bestbuy.TransactionApp.model.ShoppingCart;
import com.bestbuy.TransactionApp.repository.ShoppingCartRepository;
import com.bestbuy.TransactionApp.service.Redis.RedisCacheService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartService{
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final CartExceptionSupplier cartExceptionSupplier;
    private final CartItemExceptionSupplier cartItemExceptionSupplier;

    @Autowired
    private final RedisCacheService redisCacheService;

    public ShoppingCartResponse createShoppingCart(Long userId) {
        if(shoppingCartRepository.existsById(userId))
            throw cartExceptionSupplier.alreadyExists(userId);
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        redisCacheService.storeShoppingCart(userId, savedShoppingCart);

        return mapToShoppingCartResponse(savedShoppingCart);

    }

    private ShoppingCartResponse mapToShoppingCartResponse(ShoppingCart shoppingCart){
        return ShoppingCartResponse.builder()
                .userId(shoppingCart.getUserId())
                .createdAt(shoppingCart.getCreatedAt())
                .cartItemList(shoppingCart.getCartItemList().stream().map(cartItemService::mapToCartItemResponse).toList())
                .build();
    }

    public Boolean isShoppingCartEmpty(Long userId){
        return getShoppingCart(userId).getCartItemList().size() == 0;
    }

    public ShoppingCartResponse clearShoppingCart(Long userId){
        ShoppingCart shoppingCart = getShoppingCart(userId);
        shoppingCart.getCartItemList().clear();

        redisCacheService.storeShoppingCart(userId, shoppingCart);

        return mapToShoppingCartResponse(shoppingCartRepository.save(shoppingCart));
    }



    public CartItemResponse createCartItem(String productId, Integer quantity, Long userId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        Long cartItemId = getCartItemId(productId,shoppingCart);
         if(cartItemId!=null){
             return this.updateCartItem(userId,cartItemId,quantity);
         }
        CartItem cartItem = cartItemService.createCartItem(productId, quantity);
        shoppingCart.getCartItemList().add(cartItem);
        shoppingCartRepository.save(shoppingCart);

        redisCacheService.storeShoppingCart(userId, shoppingCart);

        return cartItemService.mapToCartItemResponse(cartItem);
    }

    public CartItemResponse updateCartItem(Long userId,Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        cartItem.setQuantity(cartItem.getQuantity()+quantity);
        if(cartItem.getQuantity()<=0){
            this.deleteCartItem(cartItemId,userId);
            return cartItemService.mapToCartItemResponse(cartItem);
        }

        redisCacheService.flushShoppingCartCache(userId);

        return cartItemService.mapToCartItemResponse(cartItemService.updateCartItem(cartItem));
    }

    private Long getCartItemId(String productId, ShoppingCart shoppingCart) {
        for(CartItem cartItem: shoppingCart.getCartItemList())
            if(cartItem.getProductId().equals(productId))
                return cartItem.getId();

        return null;
    }

    public List<ShoppingCartResponse> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        return shoppingCarts.stream().map(shoppingCart -> mapToShoppingCartResponse(shoppingCart)).toList();
    }

    public ShoppingCartResponse getShoppingCartResponse(Long userId) {
        return mapToShoppingCartResponse(getShoppingCart(userId));
    }

    public ShoppingCart getShoppingCart(Long userId) {
        ShoppingCart shoppingCart = redisCacheService.retrieveShoppingCart(userId);
        if(shoppingCart == null){
            shoppingCart = shoppingCartRepository.getShoppingCartByUserId(userId)
            .orElseThrow(cartExceptionSupplier.notFound(userId));

            redisCacheService.storeShoppingCart(userId, shoppingCart);
        }

        return shoppingCart;
    }

    public CartItemResponse deleteCartItem(Long cartItemId,Long userId ) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        int index = -1;
        for(CartItem cartItem:shoppingCart.getCartItemList()){
            if(cartItem.getId()==cartItemId){
                index = shoppingCart.getCartItemList().indexOf(cartItem);
                break;
            }
        }
        if(index == -1)
            throw cartItemExceptionSupplier.notFound(cartItemId);
        shoppingCart.getCartItemList().remove(index);
        CartItemResponse cartItemResponse = cartItemService.getCartItemResponseById(cartItemId);
        shoppingCartRepository.save(shoppingCart);

        redisCacheService.storeShoppingCart(userId, shoppingCart);

        return cartItemResponse;
    }

}
