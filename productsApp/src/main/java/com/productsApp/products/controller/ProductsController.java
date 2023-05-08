package com.productsApp.products.controller;

import com.productsApp.products.DTO.AddProductToStockRequest;
import com.productsApp.products.DTO.AuthRequest;
import com.productsApp.products.model.Product;
import com.productsApp.products.queue.AuthSender;
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

//    @GetMapping(path = "/test")
//    public String saveNewUser() {
//        return "Hello";
//    }

    private final ProductService productService;
    private final AuthSender authSender;
    private final StockSender stockSender;

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody Product product){
        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
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
        authSender.sendAuthRequest(new AuthRequest("lhdfiusdgfjsd"));
        return ResponseEntity.ok("message sent to auth successfully");
    }

    @PostMapping("/sendAddProductToStockRequest")
    public ResponseEntity testMQ2(){
        stockSender.sendAddProductRequest(new AddProductToStockRequest("lhdfiusdgfjsd","id",20,1000));
        return ResponseEntity.ok("message sent to stock successfully");
    }

}
