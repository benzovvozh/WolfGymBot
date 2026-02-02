package io.project.wolfgymbot.handlers;

import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class CommandHandler {

    private final CommandRegistry commandRegistry;
    private final TelegramExecutor telegramExecutor;

    public CommandHandler(CommandRegistry commandRegistry, TelegramExecutor telegramExecutor) {
        this.commandRegistry = commandRegistry;
        this.telegramExecutor = telegramExecutor;
    }

    public void handleTextMessage(Message message) {
        String messageText = message.getText();
        Long chatId = message.getChatId();
        String userNickname = message.getFrom().getUserName();

        Optional<BotCommand> cmd = commandRegistry.getCommand(messageText);
        if (cmd.isPresent()) {
            cmd.get().execute(chatId, userNickname);
        } else {
            commandRegistry.getCommand("/menu")
                    .ifPresent(command -> command.execute(chatId, userNickname));
        }
    }
}
