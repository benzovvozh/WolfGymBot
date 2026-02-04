package io.project.wolfgymbot.client.dto.exercise;

import io.project.wolfgymbot.service.DialogState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

// черновик упражнения
@Getter
@Setter
public class DraftExercise {
    public DraftExercise() {
        this.dialogState = DialogState.START_CREATE_EXERCISE;
    }

    private String name;
    private String description;
    private String videoUrl;
    private String muscleGroup;
    private DialogState dialogState;
}
