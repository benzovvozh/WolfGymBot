package io.project.wolfgymbot.client.dto.exercise;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// хранение черновиков упражнений через мапу
@Component
public class MapDraftExerciseStorage implements DraftExerciseStorage {
    private final Map<Long, DraftExercise> storage = new HashMap<>();

    @Override
    public DraftExercise get(Long userId) {
        return storage.getOrDefault(userId, new DraftExercise());
    }

    @Override
    public void save(Long userId, DraftExercise draftExercise) {
        storage.put(userId, draftExercise);
    }

    @Override
    public void clear(Long userId) {
        storage.remove(userId);
    }
}
