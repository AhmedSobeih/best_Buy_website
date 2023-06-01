package com.authenticationApp.authentication.controller;


import com.authenticationApp.authentication.config.JwtService;
import com.authenticationApp.authentication.dao.AuthenticationDao;
import com.authenticationApp.authentication.entity.AuthenticationEntity;
import com.authenticationApp.authentication.queue.AuthenticationReceiver;
import com.authenticationApp.authentication.queue.AuthenticationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationDao authenticationDao;
    @Autowired
    private AuthenticationSender authenticationSender;
    @Autowired
    private AuthenticationReceiver authenticationReceiver;

    @Autowired
    RedisTemplate redisTemplate;


    private final AuthenticationService service;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final JwtService jwtService;

    /*@PostMapping(path = "/test")
    public AuthenticationEntity saveNewUser(
            @RequestBody AuthenticationEntity newUser) {
        return this.authenticationDao.saveNewUser(newUser);
    }*/

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



    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(service.register(request));

        return response;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request) {
        AuthenticationResponse token=service.login(request);
        String tokenString=token.getToken();
        redisTemplate.opsForValue().set(tokenString, "");
        String filePath = "client.txt";
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(tokenString);
            writer.close();
            System.out.println("String saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        var mail = jwtService.extractUsername(tokenString);
        System.out.println(mail);

        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/logout")
    public void logout(String token) {
        //delete token from reddis


        String filePath = "client.txt";
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(path = "/changePassword")
    public ResponseEntity<AuthenticationResponse> changePassword(@RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");
        String tokenString = readTokenInClientSide();
        var mail = jwtService.extractUsername(tokenString);
        AuthenticationResponse newToken = service.changePassword(mail,newPassword);
        logout(tokenString);
        return ResponseEntity.ok(newToken);

    }




    @GetMapping(path = "/test")
    public String testing() {
        authenticationSender.sendMessage("Hello world");
        return "Hello world";
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
