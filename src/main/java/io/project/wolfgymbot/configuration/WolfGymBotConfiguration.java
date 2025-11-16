package io.project.wolfgymbot.configuration;

import io.project.wolfgymbot.bot.WolfGymBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class WolfGymBotConfiguration {
    @Bean
    public TelegramBotsApi telegramBotsApi(WolfGymBot wolfGymBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(wolfGymBot);
        return api;
    }
}
