package io.project.wolfgymbot.commands.exerciseCommands;

import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateExerciseCommand implements BotCommand {
    private final TelegramExecutor telegramExecutor;
    private final DialogStateService dialogStateService;

    public CreateExerciseCommand(TelegramExecutor telegramExecutor, DialogStateService dialogStateService) {
        this.telegramExecutor = telegramExecutor;
        this.dialogStateService = dialogStateService;
    }

    @Override
    public String getCommand() {
        return "➕ Create Exercise";
    }

    @Override
    public void execute(Long chatId, String userNickname, Long userId) {
        String messageText = "Введите название упражнения.";
        telegramExecutor.sendMessage(chatId, messageText, userNickname);
        log.info("Пользователь {} начал создание упражнения", userNickname);
        dialogStateService.createExerciseWaitName(chatId);
    }
}
