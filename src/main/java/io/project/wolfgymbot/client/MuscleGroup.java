package io.project.wolfgymbot.client;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum MuscleGroup {
    CHEST("Грудь"),
    BACK("Спина"),
    LEGS("Ноги"),
    SHOULDERS("Плечи"),
    ARMS("Руки"),
    ABS("Пресс"),
    CARDIO("Кардио"),
    FULL_BODY("Все тело");

    private final String displayName;

    MuscleGroup(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static List<MuscleGroup> getAllGroups() {
        return Arrays.asList(values());
    }
}