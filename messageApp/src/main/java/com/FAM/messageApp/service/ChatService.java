package com.FAM.messageApp.service;

import com.FAM.messageApp.dao.ChatRepository;
import com.FAM.messageApp.model.Chat;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class ChatService {
    @Autowired
    private final ChatRepository chatRepository;


    public void createChat(Chat chat){
        log.info("create chat "+ chat.getId() + " username "+ chat.getCustomerId()+ " rep ID "+ chat.getRepresentativeId());
        chatRepository.save(chat);
    }

    public Chat createChat(String customerId, String representativeId){
        Chat chat = new Chat(customerId,representativeId);
        log.info("create chat "+ chat.getId() + " username "+ chat.getCustomerId()+ " rep ID "+ chat.getRepresentativeId());
        return chatRepository.save(chat);
    }

    public Chat getChatById(String chatId){
        Optional<Chat> chat = chatRepository.findChatById(chatId);
        if(chat.isPresent()){
            log.info("return chat with id " + chatId);
            return chat.get();
        }
        log.info("couldn't find chat with id " + chatId);
        throw new IllegalStateException("Not found");
    }

    public List<Chat> getAllChatsByUserId(String id){
        Optional<List<Chat>> optionalChat = chatRepository.findChatByCustomerIdOrRepresentativeId(id,id);
        if (optionalChat.isPresent()){
            log.info("return all chats with by user id " + id);
            return optionalChat.get();
        }

        log.info("couldn't find chat for user with id " + id);
        throw new IllegalStateException("Not found");

    }
    public List<Chat> getAllChatsByCustomerId(String id) {
        Optional<List<Chat>> optionalChat = chatRepository.findChatByCustomerId(id);
        if (optionalChat.isPresent()){
            log.info("return all chats with by customer id " + id);
            return optionalChat.get();
        }
        log.info("couldn't find chat for customer with id " + id);
        throw new IllegalStateException("Not found");
    }

    public List<Chat> getAllChatsByRepresentativeId(String id) {
        Optional<List<Chat>> optionalChat = chatRepository.findChatByRepresentativeId(id);
        if (optionalChat.isPresent()){
            log.info("return all chats with by rep id " + id);
            return optionalChat.get();
        }
        log.info("couldn't find chat for rep with id " + id);
        throw new IllegalStateException("Not found");
    }

    public void deleteChatById(String id){
        log.info("delted chat with id " + id);
        chatRepository.deleteChatById(id);
    }

    public void deleteChatByUserId(String id){
        log.info("delted chats with user  id " + id);

        chatRepository.deleteChatsByCustomerId(id);
    }

    public void disableChatById(String chatId) {
        log.info("disabled chat with id " + chatId);
        Chat chat = getChatById(chatId);
        chat.setActive(false);
        chatRepository.save(chat);
    }
}