package io.project.wolfgymbot.client.dto.exercise;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExerciseDTO {
    private String name;
    private String description;
    private String muscleGroup;
    private String videoUrl;
    private LocalDateTime createdAt;
}
