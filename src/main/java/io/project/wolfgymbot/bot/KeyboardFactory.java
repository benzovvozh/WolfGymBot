package io.project.wolfgymbot.bot;

import io.project.wolfgymbot.client.MuscleGroup;
import io.project.wolfgymbot.client.dto.ExerciseDTO;
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
public class KeyboardFactory {

    // Метод для создания главного меню
    public static ReplyKeyboardMarkup createMainMenu() {
        // Создаем объект клавиатуры
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Настраиваем чтобы клавиатура всегда показывалась
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список рядов кнопок
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первый ряд кнопок
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("🏋️ Exercises"));    // Кнопка упражнений
        row1.add(new KeyboardButton("📋 Workout Templates")); // Кнопка шаблонов

        // Второй ряд кнопок (если нужно добавить еще)
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("📊 Progress"));      // Кнопка прогресса
        row2.add(new KeyboardButton("ℹ️ Help"));          // Кнопка помощи

        // Добавляем ряды в клавиатуру
        keyboard.add(row1);
        keyboard.add(row2);

        // Устанавливаем клавиатуру
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

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
        backButton.setCallbackData("exercise_back");  // Callback данные для кнопки назад
        backRow.add(backButton);
        rows.add(backRow);

        inlineKeyboard.setKeyboard(rows);  // Устанавливаем клавиатуру
        return inlineKeyboard;             // Возвращаем готовую клавиатуру
    }
}