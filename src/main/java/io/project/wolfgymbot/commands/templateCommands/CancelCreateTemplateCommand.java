package io.project.wolfgymbot.commands.templateCommands;

import io.project.wolfgymbot.client.dto.exercise.MapDraftExerciseStorage;
import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelCreateTemplateCommand implements BotCommand {

    private final DialogStateService dialogStateService;
    private final TelegramExecutor telegramExecutor;

    public CancelCreateTemplateCommand( DialogStateService dialogStateService, TelegramExecutor telegramExecutor) {
        this.dialogStateService = dialogStateService;
        this.telegramExecutor = telegramExecutor;
    }

    @Override
    public String getCommand() {
        return "❌ Отменить создание тренировки";
    }

    @Override
    public void execute(Long chatId, String userNickname, Long userId) {
        log.info("{} отменил создание тренировки", userNickname);
        dialogStateService.cancelWtCreate(chatId);
        String message = "Отмена создания тренировки";
        telegramExecutor.sendMessage(chatId,message,userNickname);
    }
}
