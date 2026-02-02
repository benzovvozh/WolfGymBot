package io.project.wolfgymbot.commands;

public interface BotCommand {
    String getCommand();
    void execute(Long chatId, String userNickname);
}
