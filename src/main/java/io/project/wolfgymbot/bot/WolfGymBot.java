package io.project.wolfgymbot.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class WolfGymBot extends TelegramLongPollingBot {
    public WolfGymBot() {
        super(getBotTokenFromEnv());
    }

    private static String getBotTokenFromEnv() {
        // Загружаем .env файл
        Dotenv dotenv = Dotenv.configure()
                .directory("D:\\WolfGym\\WolfGymBot") // укажи полный путь
                .ignoreIfMissing() // игнорировать если файла нет
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
        // твоя логика
    }

    @Override
    public String getBotUsername() {
        return "WolfGymBot";
    }
}
