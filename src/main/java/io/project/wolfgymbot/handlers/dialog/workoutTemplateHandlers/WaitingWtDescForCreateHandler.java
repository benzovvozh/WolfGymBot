package io.project.wolfgymbot.handlers.dialog.workoutTemplateHandlers;

import io.project.wolfgymbot.client.dto.template.DraftWorkoutTemplate;
import io.project.wolfgymbot.client.dto.template.MapDraftWtStorage;
import io.project.wolfgymbot.exception.TelegramExecutor;
import io.project.wolfgymbot.handlers.dialog.DialogStateHandler;
import io.project.wolfgymbot.keyboard.TemplateKeyboardFactory;
import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WaitingWtDescForCreateHandler implements DialogStateHandler {
    private final MapDraftWtStorage storage;
    private final DialogStateService dialogStateService;
    private final TelegramExecutor telegramExecutor;
    private final TemplateKeyboardFactory factory;

    public WaitingWtDescForCreateHandler(MapDraftWtStorage storage,
                                         DialogStateService dialogStateService,
                                         TelegramExecutor telegramExecutor,
                                         TemplateKeyboardFactory factory) {
        this.storage = storage;
        this.dialogStateService = dialogStateService;
        this.telegramExecutor = telegramExecutor;
        this.factory = factory;
    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_WT_DESC;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        DraftWorkoutTemplate draftWT = storage.get(userId);
        if (userInput.equals("❌ Отменить создание тренировки")) {
            log.info("{} отменил создание тренировки", userNickname);
            storage.clear(userId);
            dialogStateService.clearState(chatId);
            String message = "Вы отменили создание тренировки.";
            telegramExecutor.sendMessage(chatId, message, userNickname, factory.createTemplatesMenu());
        } else {
            log.info("{}, ввёл описание тренировки {}", userNickname, userInput);
            draftWT.setDescription(userInput.equals("⏭ Пропустить") ? null : userInput);
            storage.save(userId, draftWT);
            log.info(storage.get(userId).getDescription());
            String message = "Как вы хотите выбрать упражнение?";

            telegramExecutor.sendMessage(chatId, message, userNickname);
            dialogStateService.createWtWaitExerciseIds(chatId);
        }
    }
}
