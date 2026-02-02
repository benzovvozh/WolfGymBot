package io.project.wolfgymbot.handlers;

import io.project.wolfgymbot.commands.BotCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandRegistry {
    private final Map<String, BotCommand> commands = new HashMap<>();

    @Autowired
    public CommandRegistry(List<BotCommand> allCommands) {
        // Spring автоматом собирает ВСЕ реализации BotCommand
        for (BotCommand cmd : allCommands) {
            commands.put(cmd.getCommand(), cmd); // Кладём в Map
        }
    }

    public Optional<BotCommand> getCommand(String commandText) {
        return Optional.ofNullable(commands.get(commandText));
    }
}