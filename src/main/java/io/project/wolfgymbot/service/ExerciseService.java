package io.project.wolfgymbot.service;

import io.project.wolfgymbot.client.WorkoutApiClient;
import io.project.wolfgymbot.client.dto.ExerciseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final WorkoutApiClient apiClient;

    public List<ExerciseDTO> getAllExercises() {
        return apiClient.getExercises();
    }

    public ExerciseDTO getExerciseByName(String name) {
        var exercise = apiClient.getExerciseByName(name);
        return exercise;
    }

    public List<ExerciseDTO> getExercisesByMuscleGroup(String muscleGroup) {
        return apiClient.getExercisesByMuscleGroup(muscleGroup);
    }
}
