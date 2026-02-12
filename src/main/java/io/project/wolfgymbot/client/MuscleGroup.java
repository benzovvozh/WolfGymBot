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

    public static MuscleGroup fromDisplayName(String displayName) {
        if (displayName == null) {
            throw new IllegalArgumentException("Display name cannot be null");
        }


        // Очистка ввода и приведение к нижнему регистру
        String cleanedInput = displayName.trim().toLowerCase();

        for (MuscleGroup group : values()) {
            if (group.getDisplayName().toLowerCase().equals(cleanedInput) ||
                group.name().toLowerCase().equals(cleanedInput)) {
                return group;
            }
        }
        return null;
    }
}