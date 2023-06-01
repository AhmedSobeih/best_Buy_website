package com.authenticationApp.authentication.command;

import lombok.Data;

public interface Command {
    void execute();
    void setMessageContent(String[] value);

}
