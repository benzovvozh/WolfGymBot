package io.project.wolfgymbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DialogStateService {
    private final Map<Long, Enum> userStates = new HashMap<>();

    public void startExerciseSearch(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_NAME_FOR_SEARCH);
    }

    public DialogState getUserState(Long chatId) {
        return (DialogState) userStates.getOrDefault(chatId, DialogState.EMPTY);
    }

    public void clearState(Long chatId) {
        log.info("Очищаем состояние диалога");
        userStates.put(chatId, DialogState.EMPTY);
    }

    public void CreateExerciseWaitName(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_NAME);
    }

    public void CreateExerciseWaitDesc(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_DESC);
    }

    public void CreateExerciseWaitMuscleGroup(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_MUSCLE_GROUP);
    }

    public void CreateExerciseWaitVideoUrl(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_VIDEO_URL);
    }

    public void CreateExerciseConfirm(Long chatId) {
        userStates.put(chatId, DialogState.CONFIRM_CREATE_EXERCISE);
    }
    public void EndCreateExercise(Long chatId){
        userStates.put(chatId,DialogState.END_CREATE_EXERCISE);
    }

}
