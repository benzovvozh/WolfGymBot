package io.project.wolfgymbot.commands.templateCommands;

import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateWTCommand implements BotCommand {
    private final TelegramExecutor telegramExecutor;
    private final DialogStateService dialogStateService;

    public CreateWTCommand(TelegramExecutor telegramExecutor, DialogStateService dialogStateService) {
        this.telegramExecutor = telegramExecutor;
        this.dialogStateService = dialogStateService;
    }

    @Override
    public String getCommand() {
        return "🆕 Create Template";
    }

    @Override
    public void execute(Long chatId, String userNickname, Long userId) {
        String message = "Давай создадим шаблон тренировки \uD83D\uDCAA\n" +
                         "Введите название шаблона:";
        telegramExecutor.sendMessage(chatId,message,userNickname);
        log.info("Пользователь {} начал создание тренировки", userNickname);
        dialogStateService.createWtWaitName(chatId);
    }
}
