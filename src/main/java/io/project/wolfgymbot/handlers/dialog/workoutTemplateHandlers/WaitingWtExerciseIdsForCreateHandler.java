package io.project.wolfgymbot.handlers.dialog.workoutTemplateHandlers;

import io.project.wolfgymbot.handlers.dialog.DialogStateHandler;
import io.project.wolfgymbot.service.DialogState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WaitingWtExerciseIdsForCreateHandler implements DialogStateHandler {
    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_WT_EXERCISES;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {

    }
}
