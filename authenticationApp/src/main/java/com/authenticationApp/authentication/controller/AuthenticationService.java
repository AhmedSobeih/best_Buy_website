package com.authenticationApp.authentication.controller;


import com.authenticationApp.authentication.config.JwtService;
import com.authenticationApp.authentication.entity.AuthenticationEntity;
import com.authenticationApp.authentication.entity.Role;
import com.authenticationApp.authentication.repository.AuthenticationRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepo repository;
    //private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    RedisTemplate redisTemplate;

    public AuthenticationResponse register(RegisterRequest request) {
        //check if user exists
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            logger.info("User already exists" );
            return null;
        }
        else{
            var user = AuthenticationEntity.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            var savedUser = repository.save(user);
            logger.info("User registered" );


            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }

    }



    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )

        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
       // var refreshToken = jwtService.generateRefreshToken(user);
       // revokeAllUserTokens(user);
       // saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse logout(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )

        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        // var refreshToken = jwtService.generateRefreshToken(user);
        // revokeAllUserTokens(user);
        // saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String logout(String token) {

        String filePath = "client.txt";
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "logout successful";

        //delete token from redis
        /*
        boolean result = redisTemplate.delete(token);
        String result_txt;
        if(result)
            result_txt = "User Logged out";
        else
            result_txt = "No logged in user Exists";

        return result_txt;*/


    }

    public AuthenticationResponse changePassword(String mail, String newPassword) {
        var user = repository.findByEmail(mail)
                .orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        newPassword
                )
        );

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String readTokenInClientSide(){
        String filePath = "client.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line= reader.readLine();
            System.out.println(line);
            return line;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String authenticate(String token) {


        String usernameExist = (String) redisTemplate.opsForValue().get(token);
        System.out.println("userNameExist: " + usernameExist);
        if(usernameExist == null)
            return "null";
        var username = jwtService.extractUsername(token);
        return username;
    }

    public String getUserNameFromToken(String token)
    {
        System.out.println("trying to get username......");
        System.out.println("token: " + token);

        return authenticate(token);
    }
}
