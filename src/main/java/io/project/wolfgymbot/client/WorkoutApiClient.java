package io.project.wolfgymbot.client;

import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import io.project.wolfgymbot.client.dto.template.WorkoutTemplateDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@AllArgsConstructor
public class WorkoutApiClient {
    private final WebClient webClient;
    private final static String EX_PATH = "/exercises";
    private final static String WT_PATH = "/workout-templates";

    public List<WorkoutTemplateDTO> getWorkoutTemplates() {
        return webClient.get()
                .uri(WT_PATH)
                .retrieve()
                .bodyToFlux(WorkoutTemplateDTO.class)
                .collectList()
                .block();
    }

    public WorkoutTemplateDTO getWorkoutTemplateById(Long id) {
        return webClient.get()
                .uri(WT_PATH + id)
                .retrieve()
                .bodyToMono(WorkoutTemplateDTO.class)
                .block();
    }

    public List<ExerciseDTO> getExercises() {
        return webClient.get()
                .uri(EX_PATH)
                .retrieve()
                .bodyToFlux(ExerciseDTO.class)
                .collectList()
                .block();
    }

    public ExerciseDTO getExerciseByName(String name) {
        return webClient.get()
                .uri(EX_PATH + "/search" + "?name=" + name)
                .retrieve()
                .bodyToMono(ExerciseDTO.class)
                .block();

    }

    public List<ExerciseDTO> getExercisesByMuscleGroup(String muscleGroup) {
        return webClient.get()
                .uri(EX_PATH + "?muscleGroup=" + muscleGroup)
                .retrieve()
                .bodyToFlux(ExerciseDTO.class)
                .collectList()
                .block();
    }
}
