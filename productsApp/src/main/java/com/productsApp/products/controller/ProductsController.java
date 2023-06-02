package com.productsApp.products.controller;

import com.productsApp.products.DTO.*;

import com.productsApp.products.commands.AuthenticateCommand;
import com.productsApp.products.commands.Command;
import com.productsApp.products.commands.StockSenderCommand;
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
    public ResponseEntity createProduct(@RequestBody ProductCreateRequest productCreateRequest){
        Command c = authenticateCommand.setRequest(new AuthRequest(productCreateRequest.getToken()));
        String res=(String)c.execute();
        boolean isAdmin=checkTokenIsAdmin(res);
        if(!isAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        //TODO send transactions
        productService.createProduct(productCreateRequest);
        return ResponseEntity.ok("Product "+productCreateRequest.getProductName()+" created successfully");
    }
    @PutMapping
    public ResponseEntity updateProduct(@RequestBody ProductRUDRequest productRUDRequest){
        Command c = authenticateCommand.setRequest(new AuthRequest(productRUDRequest.getToken()));
        String res=(String)c.execute();
        boolean isAdmin=checkTokenIsAdmin(res);
        if(!isAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        //TODO send transactions
        productService.updateProduct(productRUDRequest);
        return ResponseEntity.ok("Product "+productRUDRequest.getProductName()+" updated successfully");
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(key = "#id",value = "Product")
    public void deleteProduct(@RequestBody ProductRUDRequest productRUDRequest){
        Command c = authenticateCommand.setRequest(new AuthRequest(productRUDRequest.getToken()));
        String res=(String)c.execute();
        boolean isAdmin=checkTokenIsAdmin(res);
        if(!isAdmin)
            return;
        //TODO send transactions
        productService.deleteProduct(productRUDRequest.getId());
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> searchProducts(@RequestParam("query") String query){
        return productService.searchProducts(query);
    }


    //FOR TESTING ONLY!!!


    @PostMapping("/sendAuthRequest")
    public ResponseEntity testMQ(@RequestParam("token") String token){
        //elmafrood yerga3 hena commandtype;username;userID;isLoggedIn;role empty string law mesh authenticated
        Command c = authenticateCommand.setRequest(new AuthRequest(token));
        String res=(String)c.execute();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sendAddProductToStockRequest")
    public ResponseEntity testMQ2(){
        Command c = stockSenderCommand.setRequest(new AddProductToStockRequest("lhdfiusdgfjsd","id",20,1000));
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

    public boolean checkTokenIsAdmin(String tokenResult){
        String[] split=tokenResult.split(";");
        return !split[1].equals("")&&split[4].equals("admin");
    }

    public boolean checkTokenIsUser(String tokenResult){
        String[] split=tokenResult.split(";");
        return !split[1].equals("")&&split[3].equals("true");
    }

}
