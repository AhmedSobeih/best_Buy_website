package com.userapp.commands;

import com.userapp.DTO.Request;
import com.userapp.queue.AuthSender;
import com.userapp.queue.TransactionSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionCommand extends Command {

    @Autowired
    private TransactionSender transactionSender;

    @Override
    public void execute() {
        transactionSender.sendTransactionRequest(request.getId());
    }

    public Command setRequest(Request request) {
        this.request = request;
        return this;
    }
}
