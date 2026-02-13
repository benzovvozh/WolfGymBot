package io.project.wolfgymbot.handlers.dialog.exerciseHandlers;

import io.project.wolfgymbot.client.dto.exercise.DraftExercise;
import io.project.wolfgymbot.client.dto.exercise.ExerciseRequest;
import io.project.wolfgymbot.client.dto.exercise.MapDraftExerciseStorage;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.handlers.dialog.DialogStateHandler;
import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import io.project.wolfgymbot.service.ExerciseService;
import org.springframework.stereotype.Component;

@Component
public class WaitingExerciseConfirmForCreateHandler implements DialogStateHandler {
    private final MapDraftExerciseStorage storage;
    private final DialogStateService dialogStateService;
    private final ExerciseService exerciseService;
    private final TelegramExecutor telegramExecutor;

    public WaitingExerciseConfirmForCreateHandler(MapDraftExerciseStorage storage, DialogStateService dialogStateService, ExerciseService exerciseService, TelegramExecutor telegramExecutor) {
        this.storage = storage;
        this.dialogStateService = dialogStateService;
        this.exerciseService = exerciseService;
        this.telegramExecutor = telegramExecutor;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.CONFIRM_CREATE_EXERCISE;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        if (userInput.equalsIgnoreCase("Да")) {
            DraftExercise draftExercise = storage.get(userId);
            ExerciseRequest exerciseRequest = new ExerciseRequest();
            exerciseRequest.setName(draftExercise.getName());
            exerciseRequest.setDescription(draftExercise.getDescription());
            exerciseRequest.setMuscleGroup(draftExercise.getMuscleGroup());
            exerciseRequest.setVideoUrl(draftExercise.getVideoUrl());
            exerciseRequest.setCreatedBy(userId.toString());
            exerciseService.createExercise(exerciseRequest);
            storage.clear(userId);
            dialogStateService.clearState(chatId);
            String message = "Упражнение успешно создано!";
            telegramExecutor.sendMessage(chatId, message, userNickname);
        } else if (userInput.equalsIgnoreCase("Нет")) {
            storage.clear(userId);
            dialogStateService.clearState(chatId);
            String message = "Вы отменили создание упражнения.";
            telegramExecutor.sendMessage(chatId, message, userNickname);
        }
    }
}
