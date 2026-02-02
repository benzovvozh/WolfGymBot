package io.project.wolfgymbot.commands.exerciseCommands;

import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.ExerciseKeyboardFactory;
import io.project.wolfgymbot.service.ExerciseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllExercisesMenu implements BotCommand {

    private final TelegramExecutor telegramExecutor;
    private final ExerciseService exerciseService;
    private final ExerciseKeyboardFactory keyboardFactory;

    public ShowAllExercisesMenu(TelegramExecutor telegramExecutor, ExerciseService exerciseService,
                                ExerciseKeyboardFactory keyboardFactory) {
        this.telegramExecutor = telegramExecutor;
        this.exerciseService = exerciseService;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public String getCommand() {
        return "📝 All Exercises";
    }

    @Override
    public void execute(Long chatId, String userNickname) {
        List<ExerciseDTO> exercises = exerciseService.getAllExercises();
        if (exercises.isEmpty()){
            telegramExecutor.sendMessage(chatId, "📝 Упражнений пока нет", userNickname);
        } else {
            String messageText = "🏋️ Выберите упражнение для просмотра деталей:\n\n";
            var keyboard = keyboardFactory.createExercisesInlineKeyboard(exercises);
            telegramExecutor.sendMessage(chatId, messageText, userNickname,keyboard);
        }
    }
}
