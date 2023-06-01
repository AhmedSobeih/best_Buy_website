package com.authenticationApp.authentication.controller;


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
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final AuthenticationService service;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    /*@PostMapping(path = "/test")
    public AuthenticationEntity saveNewUser(
            @RequestBody AuthenticationEntity newUser) {
        return this.authenticationDao.saveNewUser(newUser);
    }*/

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(service.register(request));
        logger.info("user registerd", response);
        return response;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = service.authenticate(request);

        //Adding token to cache
        redisTemplate.opsForValue().set(authenticationResponse.getToken(), "this is a token");

        //getting token from cache
        String value = redisTemplate.opsForValue().get(authenticationResponse.getToken());

        System.out.println(value);

        return ResponseEntity.ok(authenticationResponse);
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
