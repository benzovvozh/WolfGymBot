package io.project.wolfgymbot.handlers.dialog;


import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import io.project.wolfgymbot.service.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WaitingExerciseNameForCreateHandler implements DialogStateHandler {
    private final DialogStateService dialogStateService;
    private final ExerciseService exerciseService;

    public WaitingExerciseNameForCreateHandler(DialogStateService dialogStateService, ExerciseService exerciseService) {
        this.dialogStateService = dialogStateService;
        this.exerciseService = exerciseService;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_EXERCISE_NAME;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname) {

    }
}
