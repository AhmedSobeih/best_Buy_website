package com.productsApp.products.controller;

import com.productsApp.products.DTO.*;
import com.productsApp.products.commands.AuthenticateCommand;
import com.productsApp.products.commands.Command;
import com.productsApp.products.model.Product;
import com.productsApp.products.queue.AuthSender;
import com.productsApp.products.queue.ReviewSender;
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
    private final AuthenticateCommand authenticateCommand;
    private final StockSender stockSender;

    private final ReviewSender reviewSender;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductRUDRequest productRUDRequest){
        productService.updateProduct(productRUDRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
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
        Command c= authenticateCommand.setRequest(new AuthRequest("balabizo"));
        c.execute();
        return ResponseEntity.ok("message sent to auth successfully");
    }

    @PostMapping("/sendAddProductToStockRequest")
    public ResponseEntity testMQ2(){
        stockSender.sendAddProductRequest(new AddProductToStockRequest("lhdfiusdgfjsd","id",20,1000));
        return ResponseEntity.ok("message sent to stock successfully");
    }

    @PostMapping("/sendReviewRequest")
    public ResponseEntity testMQ3(){
        reviewSender.sendAddReviewRequest(new ReviewRequest("lhdfiusdgfjsd","id"));
        return ResponseEntity.ok("review sent to product successfully");
    }

}
