package io.project.wolfgymbot.bot.keyboard;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class CommonKeyboardFactory{

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
}