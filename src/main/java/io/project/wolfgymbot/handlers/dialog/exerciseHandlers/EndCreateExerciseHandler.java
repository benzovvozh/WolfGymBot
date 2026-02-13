package io.project.wolfgymbot.handlers.dialog.exerciseHandlers;

import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.handlers.dialog.DialogStateHandler;
import io.project.wolfgymbot.keyboard.ExerciseKeyboardFactory;
import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EndCreateExerciseHandler implements DialogStateHandler {
    private final TelegramExecutor telegramExecutor;
    private final DialogStateService dialogStateService;

    public EndCreateExerciseHandler(TelegramExecutor telegramExecutor, DialogStateService dialogStateService) {
        this.telegramExecutor = telegramExecutor;
        this.dialogStateService = dialogStateService;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.END_CREATE_EXERCISE;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        var keyboard = ExerciseKeyboardFactory.createExercisesMenu();
        telegramExecutor.sendMessage(chatId, "", userNickname, keyboard);
        dialogStateService.clearState(chatId);
    }
}
