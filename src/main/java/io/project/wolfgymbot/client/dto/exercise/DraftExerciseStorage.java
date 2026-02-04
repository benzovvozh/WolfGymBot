package io.project.wolfgymbot.client.dto.exercise;

public interface DraftExerciseStorage {
    DraftExercise get(Long userId);
    void save(Long userId, DraftExercise draftExercise);
    void clear(Long userId);
}
