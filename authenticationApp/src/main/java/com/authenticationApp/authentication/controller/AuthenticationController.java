package com.authenticationApp.authentication.controller;


import com.authenticationApp.authentication.dao.AuthenticationDao;
import com.authenticationApp.authentication.entity.AuthenticationEntity;
import com.authenticationApp.authentication.queue.AuthenticationReceiver;
import com.authenticationApp.authentication.queue.AuthenticationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationDao authenticationDao;
    @Autowired
    private AuthenticationSender authenticationSender;
    @Autowired
    private AuthenticationReceiver authenticationReceiver;

    @PostMapping(path = "/test")
    public AuthenticationEntity saveNewUser(
            @RequestBody AuthenticationEntity newUser) {
        return this.authenticationDao.saveNewUser(newUser);
    }

    @GetMapping(path = "/getAllUsers")
    public List<AuthenticationEntity> getAllUsers() {
        return this.authenticationDao.getAllUsers();
    }

    @PostMapping(path = "/sendMessage")
    public void sendMessage() {
        System.out.println("test");
        authenticationSender.sendMessage("test 2");
    }
}
