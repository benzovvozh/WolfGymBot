package io.project.wolfgymbot.commands.exerciseCommands;

import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SearchExerciseCommand implements BotCommand {
    private final TelegramExecutor telegramExecutor;
    private final DialogStateService dialogStateService;

    public SearchExerciseCommand(TelegramExecutor telegramExecutor, DialogStateService dialogStateService) {
        this.telegramExecutor = telegramExecutor;
        this.dialogStateService = dialogStateService;
    }

    @Override
    public String getCommand() {
        return "🔍 Search Exercise";
    }

    @Override
    public void execute(Long chatId, String userNickname, Long userId) {
        dialogStateService.startExerciseSearch(chatId);
        String messageText = "Введите название упражнения: ";
        telegramExecutor.sendMessage(chatId, messageText, userNickname);
        log.info("Пользователь {} начал поиск упражнения", userNickname);
    }
}
