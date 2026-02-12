package io.project.wolfgymbot.handlers;

import io.project.wolfgymbot.client.dto.exercise.MapDraftExerciseStorage;
import io.project.wolfgymbot.commands.BotCommand;
import io.project.wolfgymbot.handlers.dialog.DialogStateHandlerRegistry;
import io.project.wolfgymbot.service.DialogState;
import io.project.wolfgymbot.service.DialogStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
@Slf4j
public class CommandHandler {

    private final CommandRegistry commandRegistry;
    private final DialogStateHandlerRegistry dialogStateHandlerRegistry;
    private final DialogStateService dialogStateService;

    public CommandHandler(CommandRegistry commandRegistry, DialogStateHandlerRegistry dialogStateHandlerRegistry,
                          DialogStateService dialogStateService) {
        this.commandRegistry = commandRegistry;
        this.dialogStateHandlerRegistry = dialogStateHandlerRegistry;
        this.dialogStateService = dialogStateService;
    }

    public void handleTextMessage(Message message) {
        String messageText = message.getText();
        Long chatId = message.getChatId();
        String userNickname = message.getFrom().getUserName();
        Long userId = message.getFrom().getId();

        DialogState state = dialogStateService.getUserState(chatId);
        log.info("Состояние диалога: {}, для пользователя {}", state, userNickname);

        if (state != DialogState.EMPTY) {
            dialogStateHandlerRegistry.getHandler(state)
                    .ifPresent(handler ->
                            handler.handle(chatId, messageText, userNickname, userId));
        } else {

            Optional<BotCommand> cmd = commandRegistry.getCommand(messageText);

            if (cmd.isPresent()) {
                cmd.get().execute(chatId, userNickname, userId);
            } else {
                commandRegistry.getCommand("/menu")
                        .ifPresent(command -> command.execute(chatId, userNickname, userId));
            }
        }
    }

}
