package com.productsApp.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest implements Request{
    private String token;

    @Override
    public String requestType() {
        return "auth_request";
    }
}
