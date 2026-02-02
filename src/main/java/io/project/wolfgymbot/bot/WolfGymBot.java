package io.project.wolfgymbot.bot;

import io.github.cdimascio.dotenv.Dotenv;
import io.project.wolfgymbot.handlers.CallbackHandler;
import io.project.wolfgymbot.handlers.CommandHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.List;

@Component
public class WolfGymBot extends TelegramLongPollingBot {

    private final CallbackHandler callbackHandler;
    private final CommandHandler commandHandler;

    public WolfGymBot(CallbackHandler callbackHandler, CommandHandler commandHandler) {
        super(getBotTokenFromEnv());

        this.callbackHandler = callbackHandler;
        this.commandHandler = commandHandler;
    }

    private static String getBotTokenFromEnv() {
        // Загружаем .env файл
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // Получаем токен из .env
        String token = dotenv.get("TELEGRAM_BOT_TOKEN");

        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("TELEGRAM_BOT_TOKEN not found in .env file");
        }

        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            commandHandler.handleTextMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handleCallbackQuery(update.getCallbackQuery());
        }
    }

    @Override
    public String getBotUsername() {
        return "WolfGymBot";
    }
}
