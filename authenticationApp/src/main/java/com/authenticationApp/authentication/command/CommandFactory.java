package com.authenticationApp.authentication.command;

import com.authenticationApp.authentication.controller.AuthenticationService;

public class CommandFactory {
    public static Command createCommand(String message, AuthenticationService authenticationService) {
        Command command;
        String[] values = message.split(";");
        String commandType = values[0];
        String[] messageContent = new String[values.length - 1];
        System.arraycopy(values, 1, messageContent, 0, messageContent.length);

        switch (commandType) {
            case "authenticate":
                command = new AuthenticateCommand(authenticationService);
                break;
            default:
                throw new IllegalArgumentException("Invalid command type: " + commandType);
        }

        command.setMessageContent(messageContent);
        return command;
    }
}
