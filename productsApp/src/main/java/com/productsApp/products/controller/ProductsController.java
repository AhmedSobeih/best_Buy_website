package com.productsApp.products.controller;

import com.productsApp.products.DTO.*;

import com.productsApp.products.commands.AuthenticateCommand;
import com.productsApp.products.commands.Command;
import com.productsApp.products.commands.StockSenderCommand;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final AuthenticateCommand authenticateCommand;

    private final StockSenderCommand stockSenderCommand;
    private final StockSender stockSender;

    private final RestTemplate restTemplate;

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

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> searchProducts(@RequestParam("query") String query){
        return productService.searchProducts(query);
    }

    @PostMapping("/sendAuthRequest")
    public ResponseEntity testMQ(){
        Command c = authenticateCommand.setRequest(new AuthRequest("balabizo"));
        c.execute();
        return ResponseEntity.ok("message sent to auth successfully");
    }

    @PostMapping("/sendAddProductToStockRequest")
    public ResponseEntity testMQ2(){
        Command c = stockSenderCommand.AddProductRequest(new AddProductToStockRequest("lhdfiusdgfjsd","id",20,1000));
        c.execute();
        return ResponseEntity.ok("message sent to stock successfully");
    }

    @GetMapping("/eureka_test")
    public ResponseEntity eureka_test (){
        String resourceURL
                = "http://products-app/products";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceURL, String.class);
        System.out.println(response.getStatusCode());
        return ResponseEntity.ok("message sent");
    }

}
