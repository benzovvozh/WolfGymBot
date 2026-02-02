package io.project.wolfgymbot.handlers;

import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.ExerciseKeyboardFactory;
import io.project.wolfgymbot.service.ExerciseService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class CallbackHandler {
    private final ExerciseService exerciseService;
    private final TelegramExecutor telegramExecutor;

    public CallbackHandler(ExerciseService exerciseService, TelegramExecutor telegramExecutor) {
        this.exerciseService = exerciseService;
        this.telegramExecutor = telegramExecutor;
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String userNickname = callbackQuery.getFrom().getUserName();

        // выбор упражнения
        if (callbackData.startsWith("exercise_select_")) {
            String exerciseName = callbackData.substring("exercise_select_".length());
            exerciseService.showExerciseDetails(chatId, exerciseName, userNickname);
        }
        // поиск упражнений по группе мышц
        else if (callbackData.startsWith("muscle_group_")) {
            String muscleGroup = callbackData.substring(13); // получаем группу мышц
            List<ExerciseDTO> exerciseByMuscleGroupList = exerciseService.getExercisesByMuscleGroup(muscleGroup);
            var keyboard = ExerciseKeyboardFactory.createExercisesInlineKeyboard(exerciseByMuscleGroupList);
            telegramExecutor.sendMessage(chatId, "Выберите упражнение из списка:", userNickname, keyboard);

        }
        // Обрабатываем кнопку "Назад к списку"
        else if ("back".equals(callbackData)) {
            telegramExecutor.deleteMessage(chatId, messageId);
        }
    }
}
