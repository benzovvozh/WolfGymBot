package io.project.wolfgymbot.client.dto.template;

public interface DraftWtStorage {
    DraftWorkoutTemplate get(Long userId);

    void save(Long userId, DraftWorkoutTemplate draftWorkoutTemplate);

    void clear(Long userId);
}
