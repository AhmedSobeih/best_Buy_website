package com.bestbuy.productsApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @GetMapping
    public String getUsers()
    {
        return "product";
    }
}

