package io.project.wolfgymbot.service;

import io.project.wolfgymbot.client.WorkoutApiClient;
import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import io.project.wolfgymbot.client.dto.template.WorkoutTemplateDTO;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.ExerciseKeyboardFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class WorkoutTemplateService {
    private final WorkoutApiClient apiClient;
    private final TelegramExecutor telegramExecutor;

    public List<WorkoutTemplateDTO> getAllTemplates() {
        log.info("Поиск всех шаблонов, метод getAllTemplates -> WorkoutTemplateService");
        return apiClient.getWorkoutTemplates();
    }

    public WorkoutTemplateDTO getTemplateById(Long id) {
        return apiClient.getWorkoutTemplateById(id);
    }

    public WorkoutTemplateDTO getTemplateByName(String name) {
        return apiClient.getWorkoutTemplateByName(name);
    }

    public void showTemplateDetails(Long chatId, String templateName, String userNickname) {
        try {
            WorkoutTemplateDTO templateDTO = getTemplateByName(templateName);
            if (templateDTO == null) {
                log.info("Шаблон {} не найден", templateName);
                telegramExecutor.sendMessage(chatId, "Шаблон не найден", userNickname);

            } else {
                // Форматируем детальную информацию об упражнении
                log.info("Шаблон {} найден", templateName);
                var exerciseList = templateDTO.getExercisesIds().stream()
                        .map(id -> apiClient.getExerciseById(id))
                        .toList();
                String templateDetails = formatTemplateDetails(templateDTO, exerciseList);
                var keyboard = ExerciseKeyboardFactory.createExercisesInlineKeyboard(exerciseList);
                telegramExecutor.sendMessage(chatId, templateDetails, userNickname, keyboard);

            }

        } catch (Exception e) {
            log.info("Поиск всех упражнений, метод showTemplateDetails -> WorkoutTemplateService");
            telegramExecutor.sendMessage(chatId, "❌ Ошибка при загрузке информации шаблона", userNickname);
        }
    }

    public String formatTemplateDetails(WorkoutTemplateDTO templateDTO, List<ExerciseDTO> exerciseDTOS) {
        StringBuilder sb = new StringBuilder();
        sb.append("🏋️ <b>").append(templateDTO.getName()).append("</b>\n\n");  // Название упражнения

        if (templateDTO.getDescription() != null && !templateDTO.getDescription().isEmpty()) {
            sb.append("📝 ").append(templateDTO.getDescription()).append("\n\n");  // Описание
        }
        if (exerciseDTOS != null && !exerciseDTOS.isEmpty()) {
            sb.append("📃 Список упражнений:\n\n").append("\n\n"); // Список упражнений
        }
        return sb.toString();  // Возвращаем отформатированную строку
    }
}
