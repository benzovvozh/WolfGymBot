package io.project.wolfgymbot.commands.templateCommands;

import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.TemplateKeyboardFactory;
import io.project.wolfgymbot.service.WorkoutTemplateService;
import org.springframework.stereotype.Component;

@Component
public class TemplatesMenuCommand implements BotCommand {
    private final TelegramExecutor telegramExecutor;
    private final WorkoutTemplateService workoutTemplateService;
    private final TemplateKeyboardFactory templateKeyboardFactory;

    public TemplatesMenuCommand(TelegramExecutor telegramExecutor, WorkoutTemplateService workoutTemplateService, TemplateKeyboardFactory templateKeyboardFactory) {
        this.telegramExecutor = telegramExecutor;
        this.workoutTemplateService = workoutTemplateService;
        this.templateKeyboardFactory = templateKeyboardFactory;
    }

    @Override
    public String getCommand() {
        return "📋 Workout Templates";
    }

    @Override
    public void execute(Long chatId, String userNickname) {
        showTemplatesMenu(chatId, userNickname);
    }

    // Метод для показа меню шаблонов
    private void showTemplatesMenu(Long chatId, String userNickname) {
        String templatesText = """
                📋 Управление шаблонами тренировок
                
                Выберите действие:
                • 📋 All Templates - все шаблоны
                • 🆕 Create Template - создать новый
                • ▶️ Start Workout - начать тренировку
                • 📊 My Workouts - мои тренировки
                """;

        var keyboard = templateKeyboardFactory.createTemplatesMenu();
        telegramExecutor.sendMessage(chatId, templatesText, userNickname, keyboard);

    }

    private void showAllTemplates(Long chatId, String userNickname) {
        // Получаем все упражнения через сервис
        var templates = workoutTemplateService.getAllTemplates();
        if (templates.isEmpty()) {
            telegramExecutor.sendMessage(chatId, "📝 Шаблонов пока нет",userNickname);
        }
        // Форматируем сообщение со списком
        String messageText = "🏋️ Выберите шаблон для просмотра:\n\n";
        var keyboard = templateKeyboardFactory.createWorkoutTemplatesInlineKeyboard(templates);
        telegramExecutor.sendMessage(chatId, messageText, userNickname, keyboard);
    }
}
