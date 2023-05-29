package com.userapp.config;

import com.userapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.userapp.repository.UserRepo;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepo repository;

}
