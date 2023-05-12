package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.Chat;
import com.FAM.messageApp.model.Message;
import com.FAM.messageApp.service.ChatService;
import com.FAM.messageApp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@Slf4j
public class MessageWebsocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatService chatSessionService;

    @Autowired
    private MessageService messageService;
    @MessageMapping("/ws/chat/{chatID}")
    public void sendMessage(@DestinationVariable String chatID, Message message) {
        System.out.println("handling send message: " + message + " to: " + chatID);
        //Authenticate here
        String userName = message.getSenderId();
        //here we check if the user can send to the chat Session
        Chat chatSession = chatSessionService.getChatById(chatID);
        boolean userIsInSession = chatSession.containsUserId(userName);
        //*****
        if (userIsInSession) {
            log.info("Sending message from "+userName+"to chat with Id "+chatID);
            simpMessagingTemplate.convertAndSend("/topic/ws/chat/" + chatID, message);
        }
        else {
            log.info("failed to send as user is not in session");
        }
        //save to database Here
        messageService.saveMessage(message);
        log.info("saved to database");
    }
}