package com.userapp.commands;

import com.userapp.DTO.AuthRequest;
import com.userapp.queue.AuthSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCommand extends Command {

    @Autowired
    private AuthSender authSender;

    @Override
    public void execute() {
        authSender.sendAuthRequest((AuthRequest) request);
    }
}
