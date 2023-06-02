package com.authenticationApp.authentication.command;

import lombok.Data;

public interface Command {
    String execute();
    void setMessageContent(String[] value);

}
