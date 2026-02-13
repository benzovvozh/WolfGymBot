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
public class WaitingWtNameForCreateHandler implements DialogStateHandler {
    private final MapDraftWtStorage storage;
    private final DialogStateService dialogStateService;
    private final TelegramExecutor telegramExecutor;
    private final TemplateKeyboardFactory keyboardFactory;

    public WaitingWtNameForCreateHandler(MapDraftWtStorage storage, DialogStateService dialogStateService, TelegramExecutor telegramExecutor, TemplateKeyboardFactory keyboardFactory) {
        this.storage = storage;
        this.dialogStateService = dialogStateService;
        this.telegramExecutor = telegramExecutor;
        this.keyboardFactory = keyboardFactory;

    }

    @Override
    public boolean canHandle(DialogState state) {
        return state == DialogState.WAITING_WT_NAME;
    }

    @Override
    public void handle(Long chatId, String userInput, String userNickname, Long userId) {
        log.info("{}, ввёл название тренировки: {}", userNickname, userInput);
        DraftWorkoutTemplate draftWT = new DraftWorkoutTemplate();
        draftWT.setName(userInput);
        storage.save(userId, draftWT);
        dialogStateService.createWtWaitDesc(chatId);
        String message = "Название: "+ userInput + "\n" +
                         "Теперь введите описание шаблона\n" +
                         "(или нажмите «Пропустить»)";
        telegramExecutor.sendMessage(chatId, message, userNickname, keyboardFactory.createTemplatesDescriptionMenu());
    }
}
