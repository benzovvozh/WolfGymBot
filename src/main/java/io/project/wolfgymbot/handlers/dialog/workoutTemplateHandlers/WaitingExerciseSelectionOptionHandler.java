package io.project.wolfgymbot.handlers.dialog.workoutTemplateHandlers;

import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.handlers.dialog.DialogStateHandler;
import io.project.wolfgymbot.keyboard.TemplateKeyboardFactory;
import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WaitingExerciseSelectionOptionHandler implements DialogStateHandler {
    private final TelegramExecutor telegramExecutor;
    private final TemplateKeyboardFactory factory;
    private final DialogStateService dialogStateService;

    public WaitingExerciseSelectionOptionHandler(TelegramExecutor telegramExecutor,
                                                 TemplateKeyboardFactory factory,
                                                 DialogStateService dialogStateService) {
        this.telegramExecutor = telegramExecutor;
        this.factory = factory;
        this.dialogStateService = dialogStateService;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_WT_SELECTION_OPTION;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        switch (userInput){
            case "📋 Показать все":
                log.info("{}, выбрал Показать все", userNickname);

            case "💪 По группе мышц":
                log.info("{} выбрал Показать упражнения по группе мышц", userNickname);
            case "❌ Отменить создание тренировки":
                log.info("{} выбрал отменить создание тренировки", userNickname);
        }
    }
}
