package com.authenticationApp.authentication.controller;


import com.authenticationApp.authentication.config.JwtService;
import com.authenticationApp.authentication.entity.AuthenticationEntity;
import com.authenticationApp.authentication.entity.Role;
import com.authenticationApp.authentication.repository.AuthenticationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepo repository;
    //private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = AuthenticationEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        //var refreshToken = jwtService.generateRefreshToken(user);
        //saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        redisTemplate.opsForValue().set(jwtToken, "myValue");
       // var refreshToken = jwtService.generateRefreshToken(user);
       // revokeAllUserTokens(user);
       // saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String getUserNameFromToken()
    {
        redisTemplate.opsForValue().set("zzz", "zzz");
        return redisTemplate.opsForValue().get("zzz");
    }

}
