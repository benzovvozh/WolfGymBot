package io.project.wolfgymbot.commands.exerciseCommands;

import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.ExerciseKeyboardFactory;
import io.project.wolfgymbot.service.ExerciseService;
import org.springframework.stereotype.Component;

@Component
public class ShowExerciseMenuCommand implements BotCommand {

    private final TelegramExecutor telegramExecutor;
    private final ExerciseService exerciseService;

    public ShowExerciseMenuCommand(TelegramExecutor telegramExecutor, ExerciseService exerciseService) {
        this.telegramExecutor = telegramExecutor;
        this.exerciseService = exerciseService;
    }

    @Override
    public String getCommand() {
        return "🏋️ Exercises";
    }

    @Override
    public void execute(Long chatId, String userNickname, Long userId) {
        showExercisesMenu(chatId, userNickname);
    }

    // Метод для показа меню упражнений
    private void showExercisesMenu(Long chatId, String userNickname) {
        String exercisesText = """
                🏋️ Управление упражнениями
                
                Выберите действие:
                • 📝 My Exercises - все упражнения
                • ➕ Create Exercise - создать новое
                • 💪 By Muscle Group - по группе мышц
                • 🔍 Search Exercise - поиск упражнения
                """;

        var keyboard = ExerciseKeyboardFactory.createExercisesMenu();
        telegramExecutor.sendMessage(chatId, exercisesText, userNickname, keyboard);
    }

}
