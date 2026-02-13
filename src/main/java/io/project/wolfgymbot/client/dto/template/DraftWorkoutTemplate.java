package io.project.wolfgymbot.client.dto.template;

import io.project.wolfgymbot.service.DialogState;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DraftWorkoutTemplate {
    public DraftWorkoutTemplate(){
        this.dialogState= DialogState.START_CREATE_WT;
    }
    private String name;
    private String description;
    private List<Long> exercisesIds;
    private DialogState dialogState;
}
