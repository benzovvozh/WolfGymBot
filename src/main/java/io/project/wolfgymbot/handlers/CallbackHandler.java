package io.project.wolfgymbot.handlers;

import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.ExerciseKeyboardFactory;
import io.project.wolfgymbot.service.ExerciseService;
import io.project.wolfgymbot.service.WorkoutTemplateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public class CallbackHandler {
    private final ExerciseService exerciseService;
    private final TelegramExecutor telegramExecutor;
    private final WorkoutTemplateService workoutTemplateService;

    public CallbackHandler(ExerciseService exerciseService, TelegramExecutor telegramExecutor, WorkoutTemplateService workoutTemplateService) {
        this.exerciseService = exerciseService;
        this.telegramExecutor = telegramExecutor;
        this.workoutTemplateService = workoutTemplateService;
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
            String muscleGroup = callbackData.substring("muscle_group_".length()); // получаем группу мышц
            List<ExerciseDTO> exerciseByMuscleGroupList = exerciseService.getExercisesByMuscleGroup(muscleGroup);
            if (exerciseByMuscleGroupList.isEmpty()) {
                String exercisesNotFound = "Упражнений по группе мышц: " + muscleGroup + " не найдено";
                telegramExecutor.sendMessage(chatId, exercisesNotFound, userNickname);
            } else {
                var keyboard = ExerciseKeyboardFactory.createExercisesInlineKeyboard(exerciseByMuscleGroupList);
                telegramExecutor.sendMessage(chatId, "Выберите упражнение из списка:", userNickname, keyboard);
            }

        }
        // Выбор шаблона тренировок
        else if (callbackData.startsWith("workout_template_select_")) {
            String templateName = callbackData.substring("workout_template_select_".length());
            workoutTemplateService.showTemplateDetails(chatId, templateName, userNickname);


        }
        // Обрабатываем кнопку "Назад к списку"
        else if ("back".equals(callbackData)) {
            telegramExecutor.deleteMessage(chatId, messageId);
        }
    }
}
