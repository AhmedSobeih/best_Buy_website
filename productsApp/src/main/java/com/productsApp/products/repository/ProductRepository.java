package com.productsApp.products.repository;

import com.productsApp.products.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.*;


public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'productName': ?0}")
    Optional<Product> findByName(String name);

    @Query("{'productName': { $regex: /?0/, $options : i}}")
    List<Product> searchForProducts(String name);
}
