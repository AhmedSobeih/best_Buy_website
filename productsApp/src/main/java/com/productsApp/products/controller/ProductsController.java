package com.productsApp.products.controller;

import com.productsApp.products.DTO.*;
import com.productsApp.products.commands.AuthenticateCommand;
import com.productsApp.products.commands.Command;
import com.productsApp.products.model.Product;
import com.productsApp.products.queue.AuthSender;
import com.productsApp.products.queue.ReviewSender;
import com.productsApp.products.queue.StockSender;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
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
