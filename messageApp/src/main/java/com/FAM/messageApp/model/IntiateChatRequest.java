package com.FAM.messageApp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IntiateChatRequest {
    private String userName;


    @JsonCreator
    public IntiateChatRequest(@JsonProperty("userName")String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "IntiateChatRequest{" +
                "usernName='" + userName + '\'' +
                '}';
    }
}
