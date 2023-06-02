package com.productsApp.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest implements Request{
    //    @Field(name="name")
    private String productName;
    //    @Field(name="price")
    private BigDecimal productPrice;
    //    @Field(name="description")
    private String description;

    private String token;


    @Override
    public String requestType() {
        return "product_create_request";
    }
}
