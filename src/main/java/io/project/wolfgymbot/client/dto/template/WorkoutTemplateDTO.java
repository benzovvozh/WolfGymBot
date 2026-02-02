package io.project.wolfgymbot.client.dto.template;

import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WorkoutTemplateDTO {
    private Long workoutTemplateId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<ExerciseDTO> exercises;
}