package com.bestbuy.TransactionApp.exception;

import org.springframework.stereotype.Component;

@Component
public class AuthExceptionSupplier {
    public ApiRequestException notAdmin() {
        return new UnauthorizedException("Only admins can access this resource");
    }

    public ApiRequestException notOwner(Long userId, Long ownerId) {
        return new UnauthorizedException("User " + userId + " does not own this resource that belongs to " + ownerId);
    }

    public ApiRequestException invalidToken(String token) {
        return new UnauthorizedException("Invalid Token: " + token);
    }

}
