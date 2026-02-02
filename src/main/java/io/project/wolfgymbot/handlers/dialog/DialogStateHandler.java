package io.project.wolfgymbot.handlers.dialog;


import io.project.wolfgymbot.service.DialogState;

public interface DialogStateHandler {
    boolean canHandle(DialogState state);
    void handle(Long chatId, String userInput, String userNickname);
}
