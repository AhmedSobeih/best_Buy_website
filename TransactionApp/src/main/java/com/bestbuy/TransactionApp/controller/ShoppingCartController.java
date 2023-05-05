package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.model.ShoppingCart;
import com.bestbuy.TransactionApp.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponse createShoppingCart(@PathVariable("user_id") Long userId){
        //TODO: try and catch exceptions
        ShoppingCartResponse shoppingCartId = shoppingCartService.createShoppingCart(userId);
        log.info("Shopping Cart created with ID: {}", shoppingCartId);

        return shoppingCartId;
    }

}
