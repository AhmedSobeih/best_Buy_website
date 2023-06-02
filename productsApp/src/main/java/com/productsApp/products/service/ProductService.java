package com.productsApp.products.service;

import com.productsApp.products.DTO.ProductRUDRequest;
import com.productsApp.products.DTO.ProductCreateRequest;
import com.productsApp.products.DTO.ProductResponse;
import com.productsApp.products.model.Product;
import com.productsApp.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductCreateRequest productCreateRequest){
        Product product = Product.builder()
                .productName(productCreateRequest.getProductName())
                .productPrice(productCreateRequest.getProductPrice())
                .description(productCreateRequest.getDescription())
                .build();
        productRepository.save(product);
    }
    public void updateProduct(ProductRUDRequest productRUDRequest){
        Product product = Product.builder()
                .id(productRUDRequest.getId())
                .productName(productRUDRequest.getProductName())
                .productPrice(productRUDRequest.getProductPrice())
                .description(productRUDRequest.getDescription())
                .build();

        Product savedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find product by ID %s", product.getId())
                ));

        savedProduct.setProductName(product.getProductName() != null ? product.getProductName() : savedProduct.getProductName());
        savedProduct.setProductPrice(product.getProductPrice() != null ? product.getProductPrice() : savedProduct.getProductPrice());
        savedProduct.setDescription(product.getDescription() != null ? product.getDescription() : savedProduct.getDescription());

        productRepository.save(savedProduct);

    }
    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapProductToResponse(product)).toList();
    }

    private ProductResponse mapProductToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .description(product.getDescription())
                .build();
    }

    public Product getProductByName(String name){
        return productRepository.findByName(name).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find product by name %s", name)
        ));
    }

    public List<ProductResponse> searchProducts(String query){
         List<Product> searchResults = productRepository.searchForProducts(query);
         return searchResults.stream().map(product -> mapProductToResponse(product)).toList();
    }
    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }

    public ProductResponse getProductById(String id) {
        Product myProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find product by ID %s", id)
        ));
        return mapProductToResponse(myProduct) ;
    }
}
