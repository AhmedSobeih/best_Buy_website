package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.CartItemResponse;
import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.exception.CartExceptionSupplier;
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
public class ShoppingCartService{
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final CartExceptionSupplier cartExceptionSupplier;

    public ShoppingCartResponse createShoppingCart(Long userId) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.getShoppingCartByUserId(userId);
        if(optionalShoppingCart.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
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

    public void clearShoppingCart(Long userId){
        ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(userId);
        shoppingCart.getCartItemList().clear();
        shoppingCartRepository.save(shoppingCart);
    }



    public CartItemResponse createCartItem(String productId, Integer quantity, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(userId);
        Long checkProductInCart = checkProductInCart(productId,shoppingCart);
         if(checkProductInCart!=null){
             return this.updateCartItem(userId,checkProductInCart,quantity);
         }
        CartItem cartItem = cartItemService.createCartItem(productId, quantity);
        shoppingCart.getCartItemList().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
        return cartItemService.mapToCartItemResponse(cartItem);
    }

    public CartItemResponse updateCartItem(Long userId,Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        cartItem.setQuantity(cartItem.getQuantity()+quantity);
        if(cartItem.getQuantity()<=0){
            this.deleteCartItem(cartItemId,userId);
            return cartItemService.mapToCartItemResponse(cartItem);
        }
        return cartItemService.mapToCartItemResponse(cartItemService.updateCartItem(cartItem));
    }

    private Long checkProductInCart(String productId, ShoppingCart shoppingCart) {
        for(CartItem cartItem: shoppingCart.getCartItemList()){
            if(cartItem.getProductId().equals(productId)){
                return cartItem.getId();
            }
        }
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
        return shoppingCartRepository.getShoppingCartByUserId(userId)
                .orElseThrow(cartExceptionSupplier.notFound(userId));
    }

    public CartItemResponse deleteCartItem(Long cartItemId,Long userId ) {
        ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(userId);
        int index = -1;
        for(CartItem cartItem:shoppingCart.getCartItemList()){
            if(cartItem.getId()==cartItemId){
                index = shoppingCart.getCartItemList().indexOf(cartItem);
                break;
            }
        }
        shoppingCart.getCartItemList().remove(index);
        shoppingCartRepository.save(shoppingCart);
        return cartItemService.deleteCartItem(cartItemId);
    }

}
