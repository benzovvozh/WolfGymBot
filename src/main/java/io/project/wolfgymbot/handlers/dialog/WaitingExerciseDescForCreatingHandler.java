package io.project.wolfgymbot.handlers.dialog;

import io.project.wolfgymbot.client.dto.exercise.DraftExercise;
import io.project.wolfgymbot.client.dto.exercise.MapDraftExerciseStorage;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import io.project.wolfgymbot.service.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WaitingExerciseDescForCreatingHandler implements DialogStateHandler {

    private final MapDraftExerciseStorage storage;
    private final DialogStateService dialogStateService;
    private final TelegramExecutor telegramExecutor;

    public WaitingExerciseDescForCreatingHandler(MapDraftExerciseStorage storage, DialogStateService dialogStateService, TelegramExecutor telegramExecutor) {
        this.storage = storage;
        this.dialogStateService = dialogStateService;
        this.telegramExecutor = telegramExecutor;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_EXERCISE_DESC;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        log.info("Ожидание описания упражнения, пользователь: {}", userNickname);

        log.info("{}, ввел описание упражнения: {}", userNickname, userInput);
        DraftExercise draftExercise = storage.get(userId);
        draftExercise.setDescription(userInput);
        storage.save(userId, draftExercise);
        dialogStateService.CreateExerciseWaitMuscleGroup(chatId);
        String message = "Введите группу мышц упражнения:";
        telegramExecutor.sendMessage(chatId, message,userNickname);
    }
}
