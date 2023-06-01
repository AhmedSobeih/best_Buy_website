package com.FAM.messageApp.dao;

import com.FAM.messageApp.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat,String> {
    Optional<List<Chat>> findChatByRepresentativeId(String representativeId);
    Optional<List<Chat>> findChatByCustomerId(String customerId);
    Optional<List<Chat>> findChatByCustomerIdOrRepresentativeId(String customerId, String representativeId);

    Optional<Chat> findChatById(String chatId);
    void deleteChatById(String id);
    void deleteChatsByCustomerId(String customerId);
}
