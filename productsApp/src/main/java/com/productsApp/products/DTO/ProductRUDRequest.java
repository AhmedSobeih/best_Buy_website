package com.productsApp.products.DTO;

import lombok.Data;
import org.springframework.data.annotation.Id;

// TODO: Check design pattern for Request classes

@Data
public class ProductRUDRequest extends ProductRequest{
    @Id
    private String id;
}
