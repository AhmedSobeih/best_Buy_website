package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.exception.AuthExceptionSupplier;
import com.bestbuy.TransactionApp.queue.AuthenticationSender;
import com.bestbuy.TransactionApp.queue.responses.AuthenticationResponse;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class AuthenticatorService {
    final AuthenticationSender authenticationSender;
    final AuthExceptionSupplier authExceptionSupplier;

    private AuthenticationResponse getValidToken(String token){
        AuthenticationResponse authenticationResponse = authenticationSender.authenticateUser(token);

        if(!authenticationResponse.isValid())
            throw authExceptionSupplier.invalidToken(token);

        return authenticationResponse;

    }

    public void allowAdmin(String token){

        AuthenticationResponse authenticationResponse = getValidToken(token);
        
        if(!authenticationResponse.isAdmin()){
            throw authExceptionSupplier.notAdmin();
        }
    }

    public void allowOwnerUser(Long ownerId, String token){
        AuthenticationResponse authenticationResponse = getValidToken(token);

        if(ownerId != authenticationResponse.getUserId() && !authenticationResponse.isAdmin()){
            throw authExceptionSupplier.notOwner(ownerId, authenticationResponse.getUserId());
        }
    }
    
}
