package com.FAM.messageApp.dao;

import com.FAM.messageApp.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message,String> {
    Optional<List<Message>> findMessageByChatId(String chatId);
    void deleteMessagesByChatId(String chatId);
}