package io.project.wolfgymbot.commands.templateCommands;

import io.project.wolfgymbot.client.dto.template.WorkoutTemplateDTO;
import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.keyboard.TemplateKeyboardFactory;
import io.project.wolfgymbot.service.DialogStateService;
import io.project.wolfgymbot.service.WorkoutTemplateService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllTemplatesCommand implements BotCommand {
    private final TelegramExecutor telegramExecutor;
    private final TemplateKeyboardFactory templateKeyboardFactory;
    private final WorkoutTemplateService workoutTemplateService;

    public ShowAllTemplatesCommand(TelegramExecutor telegramExecutor, TemplateKeyboardFactory templateKeyboardFactory, WorkoutTemplateService workoutTemplateService) {
        this.telegramExecutor = telegramExecutor;
        this.templateKeyboardFactory = templateKeyboardFactory;
        this.workoutTemplateService = workoutTemplateService;
    }

    @Override
    public String getCommand() {
        return "📋 All Templates";
    }

    @Override
    public void execute(Long chatId, String userNickname) {
        List<WorkoutTemplateDTO> templates = workoutTemplateService.getAllTemplates();
        if (templates.isEmpty()) {
            telegramExecutor.sendMessage(chatId, "Шаблонов тренировок пока нет", userNickname);
        } else {
            String messageText = "🏋️ Выберите шаблон для просмотра деталей:\n\n";
            var keyboard = templateKeyboardFactory.createWorkoutTemplatesInlineKeyboard(templates);
            telegramExecutor.sendMessage(chatId, messageText, userNickname, keyboard);
        }
    }
}
