package com.productsApp.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest implements Request{
    private String token;
    private String productId;

    @Override
    public String requestType() {
        return "review_request";
    }
}
