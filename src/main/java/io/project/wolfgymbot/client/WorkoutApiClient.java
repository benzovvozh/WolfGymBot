package io.project.wolfgymbot.client;

import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import io.project.wolfgymbot.client.dto.exercise.ExerciseRequest;
import io.project.wolfgymbot.client.dto.template.WorkoutTemplateDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
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
                .block(Duration.ofSeconds(5));
    }

    public WorkoutTemplateDTO getWorkoutTemplateById(Long id) {
        return webClient.get()
                .uri(WT_PATH + id)
                .retrieve()
                .bodyToMono(WorkoutTemplateDTO.class)
                .block(Duration.ofSeconds(5));
    }

    public WorkoutTemplateDTO getWorkoutTemplateByName(String name) {
        var result = webClient.get()
                .uri(WT_PATH + "/search" + "?name=" + name)
                .retrieve()
                .bodyToMono(WorkoutTemplateDTO.class)
                .block(Duration.ofSeconds(5));
        return result;
    }

    public List<ExerciseDTO> getExercises() {
        return webClient.get()
                .uri(EX_PATH)
                .retrieve()
                .bodyToFlux(ExerciseDTO.class)
                .collectList()
                .block(Duration.ofSeconds(5));
    }

    public List<ExerciseDTO> getExercisesByUserId(Long userId) {
        return webClient.get()
                .uri(EX_PATH + "?createdBy=" + userId.toString())
                .retrieve()
                .bodyToFlux(ExerciseDTO.class)
                .collectList()
                .block(Duration.ofSeconds(5));
    }

    public ExerciseDTO getExerciseById(Long id) {
        return webClient.get()
                .uri(EX_PATH +"/"+id)
                .retrieve()
                .bodyToMono(ExerciseDTO.class)
                .block(Duration.ofSeconds(5));

    }

    public ExerciseDTO getExerciseByName(String name) {
        return webClient.get()
                .uri(EX_PATH + "/search" + "?name=" + name)
                .retrieve()
                .bodyToMono(ExerciseDTO.class)
                .block(Duration.ofSeconds(5));

    }

    public List<ExerciseDTO> getExercisesByMuscleGroup(String muscleGroup) {
        return webClient.get()
                .uri(EX_PATH + "?muscleGroup=" + muscleGroup)
                .retrieve()
                .bodyToFlux(ExerciseDTO.class)
                .collectList()
                .block(Duration.ofSeconds(5));
    }

    public ExerciseDTO createExercise(ExerciseRequest exerciseRequest) {
        log.info("WorkoutApiClient -> createExercise -> отправка запроса на создание упражнения {}",
                exerciseRequest.getName());
        ExerciseDTO exerciseDTO = webClient.post()
                .uri(EX_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exerciseRequest)
                .retrieve()
                .bodyToMono(ExerciseDTO.class)
                .block(Duration.ofSeconds(5));
        return exerciseDTO;
    }
}
