package io.project.wolfgymbot.client;

// ✅ ПРАВИЛЬНО
public class BotMessages {
    // Errors
    public static final String ERROR_SERVER_UNAVAILABLE = "❌ Сервер недоступен. Попробуйте позже.";
    public static final String ERROR_EXERCISE_NOT_FOUND = "❌ Упражнение не найдено";
    public static final String ERROR_DIALOG_DATA_LOST = "⚠️ Данные диалога потерялись. Давай начнем заново: /menu";

    // Success
    public static final String SUCCESS_EXERCISE_CREATED = "✅ Упражнение успешно создано!";

    // Prompts
    public static final String PROMPT_EXERCISE_NAME = "Введите название упражнения:";
    public static final String PROMPT_EXERCISE_DESC = "Введите описание упражнения:";
}