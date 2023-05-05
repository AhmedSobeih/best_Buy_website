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
        return shoppingCartService.getAllShoppingCarts();
    }


    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponse createShoppingCart(@PathVariable("user_id") Long userId){
        //TODO: try and catch exceptions
        ShoppingCartResponse shoppingCartResponse = shoppingCartService.createShoppingCart(userId);
        log.info("Shopping Cart created with ID: {}", shoppingCartResponse.getUserId());

        return shoppingCartResponse;
    }

    @PostMapping("/add-cart-item")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse createCartItem(@RequestBody CartItemRequest cartItemRequest){
        return shoppingCartService.createCartItem(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), cartItemRequest.getUserId());
    }

}
