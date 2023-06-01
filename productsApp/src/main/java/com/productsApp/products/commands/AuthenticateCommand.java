package com.productsApp.products.commands;

import com.productsApp.products.DTO.AuthRequest;
import com.productsApp.products.DTO.Request;
import com.productsApp.products.queue.AuthSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCommand extends Command{

    @Autowired
    private AuthSender authSender;

    @Override
    public void execute() {
        authSender.sendAuthRequest((AuthRequest) request);
    }
}
