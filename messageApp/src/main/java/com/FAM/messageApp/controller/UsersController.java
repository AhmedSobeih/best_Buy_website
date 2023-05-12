package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.CustomerRep;
import com.FAM.messageApp.service.ChatService;
import com.FAM.messageApp.service.CustomerRepService;
import com.FAM.messageApp.storage.UserStorage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Set;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@Slf4j
public class UsersController {
    ArrayList<String> customerRepsUserNames = new ArrayList<String>();
    @Autowired
    ChatService chatService;
    @Autowired
    CustomerRepService customerRepService;
    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName, HttpServletResponse response) {
        System.out.println("handling register user request: " + userName);
        customerRepsUserNames.add("Mohammed");
        customerRepsUserNames.add("Ahmed");
        customerRepsUserNames.add("Mahmoud");
        customerRepsUserNames.add("Farouk");
        try {
            UserStorage.getInstance().setUser(userName);
            if(customerRepsUserNames.contains(userName)){
                int noOfChats = chatService.getAllChatsByRepresentativeId(userName) == null ? 0 :
                        chatService.getAllChatsByRepresentativeId(userName).size();
                CustomerRep rep = new CustomerRep(1,userName,noOfChats, true);
                customerRepService.save(rep);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        return UserStorage.getInstance().getUsers();
    }
}