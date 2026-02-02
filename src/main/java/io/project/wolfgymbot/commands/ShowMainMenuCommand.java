package io.project.wolfgymbot.commands;

import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.CommonKeyboardFactory;
import org.springframework.stereotype.Component;

@Component
public class ShowMainMenuCommand implements BotCommand {
    private final TelegramExecutor telegramExecutor;
    private final CommonKeyboardFactory keyboardFactory;

    public ShowMainMenuCommand(TelegramExecutor telegramExecutor,CommonKeyboardFactory keyboardFactory) {
        this.telegramExecutor = telegramExecutor;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public String getCommand() {
        return "/menu";
    }

    @Override
    public void execute(Long chatId, String userNickname) {
        String welcomeText = """
                🏋️‍♂️ Добро пожаловать в WolfGym Bot!
                
                Выберите раздел:
                • 🏋️ Exercises - управление упражнениями
                • 📋 Workout Templates - шаблоны тренировок
                • 📊 Progress - ваш прогресс
                • ℹ️ Help - справка
                """;
        var keyboard = keyboardFactory.createMainMenu();
        telegramExecutor.sendMessage(chatId, welcomeText, userNickname, keyboard);
    }
}
