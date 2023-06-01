package com.authenticationApp.authentication.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Request{
    private String token;

    @Override
    public String requestType() {
        return "auth_request";
    }

}
