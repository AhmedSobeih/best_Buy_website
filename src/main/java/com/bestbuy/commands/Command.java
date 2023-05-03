package com.bestbuy.commands;

public interface Command {
    void execute();
    void undo();
    void redo();
}
