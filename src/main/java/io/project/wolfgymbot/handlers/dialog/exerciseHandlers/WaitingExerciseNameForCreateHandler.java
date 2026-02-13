package io.project.wolfgymbot.handlers.dialog.exerciseHandlers;

import io.project.wolfgymbot.client.dto.exercise.DraftExercise;
import io.project.wolfgymbot.client.dto.exercise.MapDraftExerciseStorage;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.handlers.dialog.DialogStateHandler;
import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WaitingExerciseNameForCreateHandler implements DialogStateHandler {
    private final MapDraftExerciseStorage storage;
    private final DialogStateService dialogStateService;
    private final TelegramExecutor telegramExecutor;


    public WaitingExerciseNameForCreateHandler(MapDraftExerciseStorage storage, DialogStateService dialogStateService, TelegramExecutor telegramExecutor) {
        this.storage = storage;
        this.dialogStateService = dialogStateService;

        this.telegramExecutor = telegramExecutor;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_EXERCISE_NAME;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        log.info("{}, ввел название упражнения: {}", userNickname, userInput);
        DraftExercise draftExercise = new DraftExercise();
        draftExercise.setName(userInput);
        storage.save(userId, draftExercise);
        dialogStateService.createExerciseWaitDesc(chatId);
        String message = "Введите описание упражнения:";
        telegramExecutor.sendMessage(chatId, message, userNickname);
    }
}
