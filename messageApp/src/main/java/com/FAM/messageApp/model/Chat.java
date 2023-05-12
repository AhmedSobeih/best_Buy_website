package com.FAM.messageApp.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Chat {
    @Id
    private String id;
    private String customerId;
    private String representativeId;
    private String title;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Chat(String customerId, String representativeId) {
        this.customerId = customerId;
        this.representativeId = representativeId;
        this.title = "title";
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean containsUserId(String id){
        return customerId.equals(id) || representativeId.equals(id);
    }
}