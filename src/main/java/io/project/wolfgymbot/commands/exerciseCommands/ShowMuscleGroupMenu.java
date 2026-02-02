package io.project.wolfgymbot.commands.exerciseCommands;

import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.MuscleGroupKeyboardFactory;
import org.springframework.stereotype.Component;

@Component
public class ShowMuscleGroupMenu implements BotCommand {

    private final TelegramExecutor telegramExecutor;
    private final MuscleGroupKeyboardFactory keyboardFactory;

    public ShowMuscleGroupMenu(TelegramExecutor telegramExecutor, MuscleGroupKeyboardFactory keyboardFactory) {
        this.telegramExecutor = telegramExecutor;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public String getCommand() {
        return "💪 By Muscle Group";
    }

    @Override
    public void execute(Long chatId, String userNickname) {
        String text = """
                💪 <b>Выберите группу мышц</b>
                
                Я покажу все упражнения для выбранной группы мышц.
                Просто нажмите на нужную группу ниже 👇
                """;

        var keyboard = keyboardFactory.createMuscleGroupsKeyboard();
        telegramExecutor.sendMessage(chatId, text, userNickname, keyboard);
    }
}
