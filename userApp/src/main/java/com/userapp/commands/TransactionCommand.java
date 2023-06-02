package com.userapp.commands;

import com.userapp.DTO.Request;
import com.userapp.queue.AuthSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionCommand extends Command {

    @Autowired
    private AuthSender authSender;

    @Override
    public void execute() {
        authSender.sendAuthRequest(request.getId(),request.getName(),request.getPassword(),request.getRole());
    }

    public Command setRequest(Request request) {
        this.request = request;
        return this;
    }
}
