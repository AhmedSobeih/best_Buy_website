package com.FAM.messageApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RedisHash("customer_rep")
public class CustomerRep implements Serializable {
    @Id
    private int id;

    private String username;
    private int noOfChats;
    private boolean isActive;
//    private String info;

}
