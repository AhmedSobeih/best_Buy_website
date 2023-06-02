package com.productsApp.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

// TODO: Check design pattern for Request classes

@Data
public class ProductRUDRequest extends ProductCreateRequest {
    @Id
    private String id;
    private String token;
}
