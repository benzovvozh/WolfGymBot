package io.project.wolfgymbot.service;

import io.project.wolfgymbot.client.WorkoutApiClient;
import io.project.wolfgymbot.client.dto.template.WorkoutTemplateDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutTemplateService {
    private final WorkoutApiClient apiClient;

    public List<WorkoutTemplateDTO> getAllTemplates() {
        return apiClient.getWorkoutTemplates();
    }
    public WorkoutTemplateDTO getTemplateById(Long id){
        return apiClient.getWorkoutTemplateById(id);
    }
}
