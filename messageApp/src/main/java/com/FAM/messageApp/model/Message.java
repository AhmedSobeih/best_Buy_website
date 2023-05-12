package com.FAM.messageApp.model;
// "customerId":"12001",
// "representativeId":"1234",
// "title":"1234",
// "active":true

//"chatId":"6453a4fa2ce8037eee1c4606",
//        "senderId":"farouk",
//        "content":"Hello how are you today"
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

@Data
@Document
@RedisHash("message_cache")
public class Message implements Serializable {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String content;
    private LocalDateTime timestamp;

    public Message(String chatId, String senderId, String content) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}