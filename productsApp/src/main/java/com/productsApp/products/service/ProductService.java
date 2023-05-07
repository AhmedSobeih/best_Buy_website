package com.productsApp.products.service;

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

    public void addProduct(Product product){
        productRepository.insert(product);
    }
    public void updateProduct(Product product){
        Product savedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot find product by ID %s", product.getId())
                ));

        savedProduct.setProductName(product.getProductName());
        savedProduct.setProductPrice(product.getProductPrice());

        productRepository.save(savedProduct);

    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductByName(String name){
        return productRepository.findByName(name).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find product by name %s", name)
        ));
    }
    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }
}
