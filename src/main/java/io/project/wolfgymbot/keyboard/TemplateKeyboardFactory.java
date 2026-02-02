package io.project.wolfgymbot.keyboard;

import io.project.wolfgymbot.client.dto.template.WorkoutTemplateDTO;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class TemplateKeyboardFactory{

    // Метод для создания меню шаблонов тренировок
    public static ReplyKeyboardMarkup createTemplatesMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("📋 All Templates"));     // Все шаблоны
        row1.add(new KeyboardButton("🆕 Create Template"));   // Создать шаблон

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("▶️ Start Workout"));     // Начать тренировку
        row2.add(new KeyboardButton("📊 My Workouts"));       // Мои тренировки

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("⬅️ Back to Main Menu")); // Назад

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup createWorkoutTemplatesInlineKeyboard(List<WorkoutTemplateDTO> templates) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < templates.size(); i += 2) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            WorkoutTemplateDTO workoutTemplateDTO1 = templates.get(i);
            var button1 = new InlineKeyboardButton();
            button1.setText(workoutTemplateDTO1.getName());
            button1.setCallbackData("workout_template_select_" + workoutTemplateDTO1.getName());
            row.add(button1);
            if (i + 1 < templates.size()) {
                var template1 = templates.get(i + 1);
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText(template1.getName());  // Устанавливаем название второй кнопки
                button2.setCallbackData("workout_template_select_" + template1.getName());  // Сохраняем название

                row.add(button2);  // Добавляем вторую кнопку в ряд
            }
            rows.add(row);
        }
        List<InlineKeyboardButton> backRow = new ArrayList<>();
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("⬅️ Назад к списку");  // Текст кнопки назад
        backButton.setCallbackData("workout_template_back");  // Callback данные для кнопки назад
        backRow.add(backButton);
        rows.add(backRow);

        inlineKeyboard.setKeyboard(rows);  // Устанавливаем клавиатуру
        return inlineKeyboard;             // Возвращаем готовую клавиатуру
    }
}
