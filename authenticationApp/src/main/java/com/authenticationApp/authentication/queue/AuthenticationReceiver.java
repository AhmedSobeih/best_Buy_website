package com.authenticationApp.authentication.queue;

import com.authenticationApp.authentication.command.AuthenticateCommand;
import com.authenticationApp.authentication.command.Command;
import com.authenticationApp.authentication.command.CommandFactory;
import com.authenticationApp.authentication.controller.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sound.midi.Receiver;

@Component
public class AuthenticationReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);


    @Autowired
    AuthenticationService authenticationService;

    @RabbitListener(queues = "AuthenticationReceiver")
    public void receiveMessage(String message) {
        handleMessage(message);
        LOGGER.info("Received message: {}", message);
        System.out.println(message);
    }

    @RabbitListener(queues = "${rabbitmq.queues.auth.name}")
    public String authenticate(String message){
        return "User authenticated! with token "+ message;
    }



    public void handleMessage(String message)
    {
        Command command = CommandFactory.createCommand(message, authenticationService);
        command.execute();
    }
}
