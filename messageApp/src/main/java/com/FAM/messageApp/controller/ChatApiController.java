package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.Chat;
import com.FAM.messageApp.model.CustomerRep;
import com.FAM.messageApp.model.IntiateChatRequest;
import com.FAM.messageApp.service.ChatService;
import com.FAM.messageApp.service.CustomerRepService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chat")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ChatApiController {
//    private final ChatService chatService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private CustomerRepService customerRepService;

    @GetMapping("/{chatId}")
    public Chat getAllChatsByChId(@PathVariable String chatId){
        return chatService.getChatById(chatId);
    }

    @GetMapping("/user/{id}")
    public List<Chat> getAllChatsByUserId(@PathVariable String id){
        return chatService.getAllChatsByUserId(id);
    }

    @GetMapping("/customer/{id}")
    public List<Chat> getAllChatsByCustomerId(@PathVariable String id){
        return chatService.getAllChatsByCustomerId(id);
    }

    @PutMapping("/{chatId}/disable")
    public void disableChat(@PathVariable String chatId){
        chatService.disableChatById(chatId);
    }

    @GetMapping("/representative/{id}")
    public List<Chat> getAllChatsByRepresentativeId(@PathVariable String id){
        return chatService.getAllChatsByRepresentativeId(id);
    }
    @PostMapping
    public void saveChat(@RequestBody Chat chat){
        chatService.createChat(chat);
    }
    @DeleteMapping("/{id}")
    public void deleteChatById(@PathVariable String id){
        chatService.deleteChatById(id);

    }
    @DeleteMapping("/user/{id}")
    public void deleteChatByUserId(@PathVariable String id){
        chatService.deleteChatById(id);
    }

    @PostMapping(path = "/initiate")
    public ResponseEntity<String> intiateChat(@RequestBody IntiateChatRequest requestBody, HttpServletRequest request)
    {
        System.out.println("handling intiate chat request: " );
        System.out.println(requestBody);

        String userName = requestBody.getUserName();

        //Here we should find the user that would be matched

        //Here We should create the chat session
        Chat chatSession;
        try {
            String matchedUser = findUser();
            chatSession= chatService.createChat(userName,matchedUser);
            System.out.println("Chat session created " + chatSession.getId()+"  " +matchedUser);
            System.out.println(chatService.getChatById(chatSession.getId()));
            //we send to the matched user a message, so they subscribe to the chatsession
            messagingTemplate.convertAndSend("/topic/user/" + matchedUser, chatSession.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(chatSession.getId());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create chat session: " + e.getMessage());
        }
    }

    private String findUser() throws Exception {
        CustomerRep customerRep = customerRepService.findCustomerRep();
        if (customerRep!=null)
        {
            return customerRep.getUsername();
        }
        else
            throw new Exception("No available customer service representaives");

    }

}