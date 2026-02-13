package io.project.wolfgymbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.LongConsumer;

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

    public void createExerciseWaitName(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_NAME);
    }

    public void createExerciseWaitDesc(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_DESC);
    }

    public void createExerciseWaitMuscleGroup(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_MUSCLE_GROUP);
    }

    public void createExerciseWaitVideoUrl(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_EXERCISE_VIDEO_URL);
    }

    public void createExerciseConfirm(Long chatId) {
        userStates.put(chatId, DialogState.CONFIRM_CREATE_EXERCISE);
    }

    public void endCreateExercise(Long chatId) {
        userStates.put(chatId, DialogState.END_CREATE_EXERCISE);
    }

    public void createWtWaitName(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_WT_NAME);
    }

    public void createWtWaitDesc(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_WT_DESC);
    }

    public void createWtWaitExerciseIds(Long chatId) {
        userStates.put(chatId, DialogState.WAITING_WT_EXERCISES);
    }
    public void cancelWtCreate(Long chatId){
        userStates.put(chatId, DialogState.CANCEL_WT_CREATE);
    }
    public  void  skipWtDescCreate(Long chatId){
        userStates.put(chatId, DialogState.SKIP_WT_DESC);
    }
}
