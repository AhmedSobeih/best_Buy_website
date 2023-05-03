package com.authenticationApp.authentication.dao;

import com.authenticationApp.authentication.entity.AuthenticationEntity;
import com.authenticationApp.authentication.repository.AuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthenticationDao {
    @Autowired
    private AuthenticationRepo authenticationRepo;

    public AuthenticationEntity saveNewUser(AuthenticationEntity newUser)
    {
        return authenticationRepo.save(newUser);
    }

    public List<AuthenticationEntity> getAllUsers() {
        return authenticationRepo.findAll();
    }
}
