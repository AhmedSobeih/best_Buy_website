package com.authenticationApp.authentication.command;

public class CommandFactory {
    public static Command createCommand(String message) {
        Command command;
        String[] values = message.split(";");
        String commandType = values[0];
        String[] messageContent = new String[values.length - 1];
        System.arraycopy(values, 1, messageContent, 0, messageContent.length);

        switch (commandType) {
            case "authenticate":
                command = new AuthenticateCommand();
                break;
            default:
                throw new IllegalArgumentException("Invalid command type: " + commandType);
        }

        command.setMessageContent(messageContent);
        return command;
    }
}
