package io.project.wolfgymbot.handlers.dialog.exerciseHandlers;

import io.project.wolfgymbot.client.MuscleGroup;
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
public class WaitingExerciseMuscleGroupForCreateHandler implements DialogStateHandler {
    private final MapDraftExerciseStorage storage;
    private final DialogStateService dialogStateService;
    private final TelegramExecutor telegramExecutor;

    public WaitingExerciseMuscleGroupForCreateHandler(MapDraftExerciseStorage storage, DialogStateService dialogStateService, TelegramExecutor telegramExecutor) {
        this.storage = storage;
        this.dialogStateService = dialogStateService;
        this.telegramExecutor = telegramExecutor;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_EXERCISE_MUSCLE_GROUP;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        log.info("Ожидание группы мышц упражнения, пользователь: {}", userNickname);

        log.info("{}, ввел группу мышц упражнения: {}", userNickname, userInput);
        DraftExercise draftExercise = storage.get(userId);
        draftExercise.setMuscleGroup(MuscleGroup.fromDisplayName(userInput).name());
        storage.save(userId, draftExercise);
        dialogStateService.createExerciseWaitVideoUrl(chatId);
        String message = "Введите ссылку для упражнения:";
        telegramExecutor.sendMessage(chatId, message, userNickname);
    }
}
