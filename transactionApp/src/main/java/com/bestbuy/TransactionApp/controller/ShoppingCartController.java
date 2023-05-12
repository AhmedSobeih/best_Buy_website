package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.CartItemRequest;
import com.bestbuy.TransactionApp.dto.CartItemResponse;
import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.service.ShoppingCartService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Shopping Cart Controller")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingCartResponse> getAllShoppingCarts(){
        log.info("Sent all shopping carts");
        return shoppingCartService.getAllShoppingCarts();
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartResponse getShoppingCart(@PathVariable("user_id") Long userId){
        log.info("Sent shopping cart of user with id: {}", userId);
        return shoppingCartService.getShoppingCart(userId);
    }


    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponse createShoppingCart(@PathVariable("user_id") Long userId){
        log.info("Created shopping cart for user: {}", userId);
        return shoppingCartService.createShoppingCart(userId);
    }

    @PostMapping("/add-cart-item")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse createCartItem(@RequestBody CartItemRequest cartItemRequest){
        log.info("Added cart item: {}", cartItemRequest);
        return shoppingCartService.createCartItem(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), cartItemRequest.getUserId());
    }

    @DeleteMapping("/delete-cart-item/{cart_item_id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponse deleteCartItem(@PathVariable ("cart_item_id") Long cartItemId){
        log.info("Removed cart item: {}", cartItemId);
        return shoppingCartService.deleteCartItem(cartItemId);
    }

    @PutMapping("/update-cart-item")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponse updateCartItem(@RequestBody CartItemRequest cartItemRequest){
        log.info("Updated cart item: {}", cartItemRequest);
        return shoppingCartService.updateCartItem(cartItemRequest.getId(),cartItemRequest.getQuantity());
    }

}
