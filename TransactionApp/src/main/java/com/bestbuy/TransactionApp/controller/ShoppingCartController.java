package com.bestbuy.TransactionApp.controller;

import com.bestbuy.TransactionApp.dto.CartItemRequest;
import com.bestbuy.TransactionApp.dto.CartItemResponse;
import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.service.AuthenticatorService;
import com.bestbuy.TransactionApp.service.ShoppingCartService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart Controller")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
    private final AuthenticatorService authenticatorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingCartResponse> getAllShoppingCarts(@RequestHeader("userToken") String userToken){
        authenticatorService.allowAdmin(userToken);

        logger.info("Sent all shopping carts");
        return shoppingCartService.getAllShoppingCarts();
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartResponse getShoppingCart(@RequestHeader("userToken") String userToken, @PathVariable("user_id") Long userId){
        authenticatorService.allowOwnerUser(userId, userToken);

        logger.info("Sent shopping cart of user with id: {}", userId);
        return shoppingCartService.getShoppingCartResponse(userId);
    }


    @PostMapping("/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponse createShoppingCart(@RequestHeader("userToken") String userToken, @PathVariable("user_id") Long userId){
        authenticatorService.allowOwnerUser(userId, userToken);

        logger.info("Created shopping cart for user: {}", userId);
        return shoppingCartService.createShoppingCart(userId);
    }

    @PostMapping("/clear/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartResponse clearShoppingCart(@RequestHeader("userToken") String userToken, @PathVariable("user_id") Long userId){
        authenticatorService.allowOwnerUser(userId, userToken);

        logger.info("Cleared shopping cart for user: {}", userId);
        return shoppingCartService.clearShoppingCart(userId);
    }

    @PostMapping("/add-cart-item")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse createCartItem(@RequestHeader("userToken") String userToken, @RequestBody CartItemRequest cartItemRequest){
        authenticatorService.allowOwnerUser(cartItemRequest.getUserId(), userToken);

        logger.info("Added cart item: {}", cartItemRequest);
        return shoppingCartService.createCartItem(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), cartItemRequest.getUserId());
    }

    @DeleteMapping("/delete-cart-item/{user_id}/{cart_item_id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponse deleteCartItem(@RequestHeader("userToken") String userToken, @PathVariable ("cart_item_id") Long cartItemId,@PathVariable ("user_id") Long userId){
        authenticatorService.allowOwnerUser(userId, userToken);
        
        logger.info("Removed cart item: {}", cartItemId);
        return shoppingCartService.deleteCartItem(cartItemId,userId);
    }

    @PutMapping("/update-cart-item")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponse updateCartItem(@RequestHeader("userToken") String userToken, @RequestBody CartItemRequest cartItemRequest){
        authenticatorService.allowOwnerUser(cartItemRequest.getUserId(), userToken);

        logger.info("Updated cart item: {}", cartItemRequest);
        return shoppingCartService.updateCartItem(cartItemRequest.getUserId(),cartItemRequest.getId(),cartItemRequest.getQuantity());
    }

}
