package io.project.wolfgymbot.bot.keyboard;

import io.project.wolfgymbot.client.MuscleGroup;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MuscleGoupKeyboardFactory {

    public static InlineKeyboardMarkup createMuscleGroupsKeyboard() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Получаем все группы мышц
        List<MuscleGroup> muscleGroups = MuscleGroup.getAllGroups();

        // Группируем по 2 кнопки в ряд для компактности
        for (int i = 0; i < muscleGroups.size(); i += 2) {
            List<InlineKeyboardButton> row = new ArrayList<>();

            // Первая кнопка в ряду
            MuscleGroup group1 = muscleGroups.get(i);
            InlineKeyboardButton button1 = new InlineKeyboardButton();
            button1.setText(group1.getDisplayName());
            button1.setCallbackData("muscle_group_" + group1.name());

            row.add(button1);

            // Вторая кнопка в ряду (если есть следующая группа)
            if (i + 1 < muscleGroups.size()) {
                MuscleGroup group2 = muscleGroups.get(i + 1);
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText(group2.getDisplayName());
                button2.setCallbackData("muscle_group_" + group2.name());

                row.add(button2);
            }

            rows.add(row);
        }
        // Добавляем кнопку "Назад"
        List<InlineKeyboardButton> backRow = new ArrayList<>();
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("⬅️ Назад");
        backButton.setCallbackData("muscle_group_back");
        backRow.add(backButton);
        rows.add(backRow);

        inlineKeyboard.setKeyboard(rows);
        return inlineKeyboard;
    }
}
