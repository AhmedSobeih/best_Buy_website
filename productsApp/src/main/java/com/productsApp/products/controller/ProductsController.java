package com.productsApp.products.controller;

import com.productsApp.products.DTO.*;
import com.productsApp.products.model.Product;
import com.productsApp.products.queue.AuthSender;
import com.productsApp.products.queue.StockSender;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.productsApp.products.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final AuthSender authSender;
    private final StockSender stockSender;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody ProductRUDRequest productRUDRequest){
        productService.updateProduct(productRUDRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(key = "#id",value = "Product")
    public ProductResponse getProductId(@PathVariable String id){
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(key = "#id",value = "Product")
    public void deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
    }

    @PostMapping("/sendAuthRequest")
    public ResponseEntity testMQ(){
        authSender.sendAuthRequest(new AuthRequest("lhdfiusdgfjsd"));
        return ResponseEntity.ok("message sent to auth successfully");
    }

    @PostMapping("/sendAddProductToStockRequest")
    public ResponseEntity testMQ2(){
        stockSender.sendAddProductRequest(new AddProductToStockRequest("lhdfiusdgfjsd","id",20,1000));
        return ResponseEntity.ok("message sent to stock successfully");
    }

}
