package io.project.wolfgymbot.client.dto.exercise;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRequest {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String muscleGroup;
    private String videoUrl;
    private String createdBy;
}
