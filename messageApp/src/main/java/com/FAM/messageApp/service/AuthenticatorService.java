package com.FAM.messageApp.service;


import com.FAM.messageApp.queue.AuthenticationSender;
import com.FAM.messageApp.queue.responses.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticatorService {
    final AuthenticationSender authenticationSender;

    private AuthenticationResponse getValidToken(String token){
        AuthenticationResponse authenticationResponse = authenticationSender.authenticateUser(token);

        if(!authenticationResponse.isValid())
            log.info("invalid Token");

        return authenticationResponse;

    }

    public void allowAdmin(String token){

        AuthenticationResponse authenticationResponse = getValidToken(token);
        
        if(!authenticationResponse.isAdmin()){
            log.info("not Admin");
        }
    }

    public void allowOwnerUser(Long ownerId, String token){
        AuthenticationResponse authenticationResponse = getValidToken(token);

        if(ownerId != authenticationResponse.getUserId() && !authenticationResponse.isAdmin()){
                log.info("Not the owner");
        }
    }
    
}
