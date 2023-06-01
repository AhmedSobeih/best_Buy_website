package com.authenticationApp.authentication.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.sound.midi.Receiver;

@Component
public class AuthenticationReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    @RabbitListener(queues = "AuthenticationReceiver")
    public void receiveMessage(String message) {
        LOGGER.info("Received message: {}", message);
        System.out.println(message);
    }
}
