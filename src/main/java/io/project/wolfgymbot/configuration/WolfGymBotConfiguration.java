package io.project.wolfgymbot.configuration;

import io.netty.channel.ChannelOption;
import io.project.wolfgymbot.bot.WolfGymBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WolfGymBotConfiguration {
    @Bean
    public TelegramBotsApi telegramBotsApi(WolfGymBot wolfGymBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(wolfGymBot);
        return api;
    }

    @Bean
    public WebClient webClient(@Value("${api.base-url}") String baseUrl) {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5))  // ✅ Глобальный таймаут
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);  // 3 сек на подключение

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
