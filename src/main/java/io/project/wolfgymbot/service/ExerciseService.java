package io.project.wolfgymbot.service;

import io.project.wolfgymbot.client.WorkoutApiClient;
import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import io.project.wolfgymbot.exception.TelegramExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ExerciseService {

    private final WorkoutApiClient apiClient;
    private final TelegramExecutor telegramExecutor;

    public List<ExerciseDTO> getAllExercises() {
        return apiClient.getExercises();
    }

    public ExerciseDTO getExerciseByName(String name) {
        var exercise = apiClient.getExerciseByName(name);
        return exercise;
    }

    public List<ExerciseDTO> getExercisesByMuscleGroup(String muscleGroup) {
        return apiClient.getExercisesByMuscleGroup(muscleGroup);
    }

    public void showExerciseDetails(Long chatId,String exerciseName, String userNickname) {
        try {
            // Получаем упражнение по названию через сервис
            ExerciseDTO exercise = getExerciseByName(exerciseName);

            if (exercise == null) {
                log.info("Упражнение {} не найдено", exerciseName);
                telegramExecutor.sendMessage(chatId, "Упражнение не найдено", userNickname);

            } else {
                // Форматируем детальную информацию об упражнении
                log.info("Упражнение {} найдено", exerciseName);
                String exerciseDetails = formatExerciseDetails(exercise);
                telegramExecutor.sendMessage(chatId, exerciseDetails,userNickname);

            }
        } catch (Exception e) {
            log.info("Ошибка в методе showExerciseDetails -> ExerciseService");
            telegramExecutor.sendMessage(chatId, "❌ Ошибка при загрузке информации об упражнении", userNickname);
        }
    }

    // Отправляем красивое сообщение упражнения
    private String formatExerciseDetails(ExerciseDTO exercise) {
        StringBuilder sb = new StringBuilder();
        sb.append("🏋️ <b>").append(exercise.getName()).append("</b>\n\n");  // Название упражнения

        if (exercise.getDescription() != null && !exercise.getDescription().isEmpty()) {
            sb.append("📝 ").append(exercise.getDescription()).append("\n\n");  // Описание
        }

        if (exercise.getMuscleGroup() != null) {
            sb.append("💪 Группа мышц: ").append(exercise.getMuscleGroup()).append("\n");  // Группа мышц
        }

        if (exercise.getVideoUrl() != null && !exercise.getVideoUrl().isEmpty()) {
            sb.append("🎥 Видео: ").append(exercise.getVideoUrl()).append("\n");  // Ссылка на видео
        }

        return sb.toString();  // Возвращаем отформатированную строку
    }
}
