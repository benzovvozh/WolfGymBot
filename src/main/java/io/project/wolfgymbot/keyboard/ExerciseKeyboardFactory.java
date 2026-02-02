package io.project.wolfgymbot.keyboard;

import io.project.wolfgymbot.client.dto.exercise.ExerciseDTO;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExerciseKeyboardFactory{

    // Метод для создания меню упражнений
    public static ReplyKeyboardMarkup createExercisesMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        // Ряд с кнопками упражнений
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("📝 All Exercises"));     // Все упражнения
        row1.add(new KeyboardButton("➕ Create Exercise"));   // Создать упражнение

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("💪 By Muscle Group"));  // По группе мышц
        row2.add(new KeyboardButton("🔍 Search Exercise"));  // Поиск упражнения

        // Кнопка возврата в главное меню
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("⬅️ Back to Main Menu")); // Назад

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup createExercisesInlineKeyboard(List<ExerciseDTO> exercises) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();  // Создаем inline клавиатуру
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();         // Создаем список рядов

        // Группируем упражнения по 2 в ряд для компактности
        for (int i = 0; i < exercises.size(); i += 2) {
            List<InlineKeyboardButton> row = new ArrayList<>();  // Создаем новый ряд

            // Первая кнопка в ряду
            ExerciseDTO exercise1 = exercises.get(i);
            InlineKeyboardButton button1 = new InlineKeyboardButton();
            button1.setText(exercise1.getName());  // Устанавливаем название упражнения как текст кнопки
            button1.setCallbackData("exercise_select_" + exercise1.getName());  // Сохраняем название в callback

            row.add(button1);  // Добавляем первую кнопку в ряд

            // Вторая кнопка в ряду (если есть следующее упражнение)
            if (i + 1 < exercises.size()) {
                ExerciseDTO exercise2 = exercises.get(i + 1);
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText(exercise2.getName());  // Устанавливаем название второй кнопки
                button2.setCallbackData("exercise_select_" + exercise2.getName());  // Сохраняем название

                row.add(button2);  // Добавляем вторую кнопку в ряд
            }

            rows.add(row);  // Добавляем ряд в клавиатуру
        }

        // Добавляем кнопку "Назад" в отдельный ряд
        List<InlineKeyboardButton> backRow = new ArrayList<>();
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("⬅️ Назад к списку");  // Текст кнопки назад
        backButton.setCallbackData("back");  // Callback данные для кнопки назад
        backRow.add(backButton);
        rows.add(backRow);

        inlineKeyboard.setKeyboard(rows);  // Устанавливаем клавиатуру
        return inlineKeyboard;             // Возвращаем готовую клавиатуру
    }
}
