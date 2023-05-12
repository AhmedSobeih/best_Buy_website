package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.Message;
import com.FAM.messageApp.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@RequestMapping("api/message")
@AllArgsConstructor
@Slf4j
public class MessageApiController {
    private final MessageService messageService;
    @GetMapping("/{chatId}")
    public List<Message> getMessageByChatId(@PathVariable String chatId){
        try {
            log.info("returning message with id : " + chatId);
            return messageService.getAllMessagesForChat(chatId);
        }
        catch (Exception e)
        {
            log.info("Failed to return message with Id: " + chatId);
            return  new ArrayList<>();
        }

    }
    @PostMapping
    public void postMessage(@RequestBody Message message){
        messageService.saveMessage(message);
    }
    @DeleteMapping("/{chatId}")
    public void deleteMessages(@PathVariable String chatId){
        messageService.deleteMessagesByChatId(chatId);
    }
}