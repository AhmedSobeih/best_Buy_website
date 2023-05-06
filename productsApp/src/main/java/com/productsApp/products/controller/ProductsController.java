package com.productsApp.products.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/products")
public class ProductsController {

    @GetMapping(path = "/test")
    public String saveNewUser() {
        return "Hello";
    }
}
