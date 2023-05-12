package com.authenticationApp.authentication.controller;


import com.authenticationApp.authentication.dao.AuthenticationDao;
import com.authenticationApp.authentication.entity.AuthenticationEntity;
import com.authenticationApp.authentication.queue.AuthenticationReceiver;
import com.authenticationApp.authentication.queue.AuthenticationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationDao authenticationDao;
    @Autowired
    private AuthenticationSender authenticationSender;
    @Autowired
    private AuthenticationReceiver authenticationReceiver;

    private final AuthenticationService service;

    /*@PostMapping(path = "/test")
    public AuthenticationEntity saveNewUser(
            @RequestBody AuthenticationEntity newUser) {
        return this.authenticationDao.saveNewUser(newUser);
    }*/

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }



    /*@GetMapping(path = "/getAllUsers")
    public List<AuthenticationEntity> getAllUsers() {
        return this.authenticationDao.getAllUsers();
    }

    @PostMapping(path = "/sendMessage")
    public void sendMessage() {
        System.out.println("test");
        authenticationSender.sendMessage("test 2");
    }*/
}
