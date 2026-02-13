package io.project.wolfgymbot.client.dto.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MapDraftWtStorage implements DraftWtStorage {
    private final Map<Long, DraftWorkoutTemplate> storage = new HashMap<>();

    @Override
    public DraftWorkoutTemplate get(Long userId) {
        return storage.getOrDefault(userId, new DraftWorkoutTemplate());
    }

    @Override
    public void save(Long userId, DraftWorkoutTemplate draftWorkoutTemplate) {
        storage.put(userId, draftWorkoutTemplate);
    }

    @Override
    public void clear(Long userId) {
        storage.remove(userId);
    }
}
