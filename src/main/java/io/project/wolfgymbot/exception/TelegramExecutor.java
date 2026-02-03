package io.project.wolfgymbot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramExecutor {
    private TelegramLongPollingBot bot;
    private static final Logger log = LoggerFactory.getLogger(TelegramExecutor.class);

    public TelegramExecutor(@Lazy TelegramLongPollingBot bot) {
        this.bot = bot;
    }
    // метод создания сообщения с установкой клавиатуры
    public void sendMessage(Long chatId,
                                   String text,
                                   String userNickname,
                                   ReplyKeyboard keyboard) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        message.setParseMode("HTML");
        message.setReplyMarkup(keyboard);
        executeWithErrorHandling(message, chatId, "sendMessage", userNickname);
    }
    // метод создания сообщения
    public void sendMessage(Long chatId, String text, String userNickname) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        message.setParseMode("HTML");
        executeWithErrorHandling(message, chatId, "sendMessage", userNickname);
    }
    /*
    Метод для отправки сообщений с обработкой ошибок
    Принимает:
              метод TG,
              айди чата,
              название метода для логов,
              никнейм юзера для логов
     */
    private void executeWithErrorHandling(BotApiMethod<?> method, Long chatId,
                                          String methodName, String userNickname) {
        try {
            bot.execute(method);
            log.info("✅ Успешно: {} для чата {}. Пользователь: {}", methodName, chatId, userNickname);

        } catch (TelegramApiException e) {
            // 1. Классифицируем ошибку
            TelegramErrorType errorType = classifyError(e);

            // 2. Логируем с контекстом
            log.error(String.valueOf(errorType), e, chatId, methodName);

            // 3. Решаем, что делать дальше
            handleErrorBasedOnType(errorType, e, chatId, methodName);
        }
    }

    // Классификация ошибок, именно АПИ ТГ
    private TelegramErrorType classifyError(TelegramApiException e) {
        String errorMessage = e.getMessage();
        if (errorMessage.contains("400")) {
            return TelegramErrorType.UNKNOWN_ERROR;
        }
        return null;
    }

    // обработка ошибок тг
    private void handleErrorBasedOnType(TelegramErrorType errorType,
                                        TelegramApiException e,
                                        Long chatId,
                                        String methodName) {
        switch (errorType) {
            case UNKNOWN_ERROR -> {
                sendErrorMessageToUser(chatId, "Неизвестная ошибка");
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + errorType);
        }
    }

    // отправка сообщения об ошибке
    private void sendErrorMessageToUser(Long chatId, String errorText) {
        try {
            SendMessage errorMessage = new SendMessage(chatId.toString(),
                    "❌ " + errorText + "\n\nЕсли ошибка повторяется, сообщите разработчику.");
            bot.execute(errorMessage);
        } catch (TelegramApiException ex) {
            log.error("Не удалось отправить сообщение об ошибке пользователю {}", chatId, ex);
        }
    }

    // Вспомогательный метод для удаления сообщения
    public void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage(chatId.toString(), messageId);
        try {
            bot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
