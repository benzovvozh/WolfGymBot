package io.project.wolfgymbot.handlers.dialog;

import io.project.wolfgymbot.service.DialogState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DialogStateHandlerRegistry {
    private final List<DialogStateHandler> handlers;

    @Autowired
    public DialogStateHandlerRegistry(List<DialogStateHandler> handlers) {
        this.handlers = handlers;
    }

    public Optional<DialogStateHandler> getHandler(DialogState state) {
        return handlers.stream()
                .filter(h -> h.canHandle(state))
                .findFirst();
    }
}