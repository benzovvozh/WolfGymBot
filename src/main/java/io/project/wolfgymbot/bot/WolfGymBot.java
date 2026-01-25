package io.project.wolfgymbot.bot;

import io.github.cdimascio.dotenv.Dotenv;
import io.project.wolfgymbot.client.dto.ExerciseDTO;
import io.project.wolfgymbot.service.ExerciseService;
import io.project.wolfgymbot.service.WorkoutTemplateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class WolfGymBot extends TelegramLongPollingBot {

    private final ExerciseService exerciseService;
    private final WorkoutTemplateService workoutTemplateService;

    public WolfGymBot(ExerciseService exerciseService, WorkoutTemplateService workoutTemplateService) {
        super(getBotTokenFromEnv());
        this.workoutTemplateService = workoutTemplateService;
        this.exerciseService = exerciseService;
    }

    private static String getBotTokenFromEnv() {
        // Загружаем .env файл
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // Получаем токен из .env
        String token = dotenv.get("TELEGRAM_BOT_TOKEN");

        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("TELEGRAM_BOT_TOKEN not found in .env file");
        }

        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update.getMessage());  // Обрабатываем текстовые сообщения
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());  // Обрабатываем нажатия кнопок
        }
    }

    private void handleTextMessage(Message message) {
        String messageText = message.getText();  // Получаем текст сообщения
        Long chatId = message.getChatId();       // Получаем ID чата

        // Обрабатываем разные команды меню
        switch (messageText) {
            case "/start":

            case "/menu":
                showMainMenu(chatId);
                break;

            case "⬅️ Back to Main Menu":
                showMainMenu(chatId);  // Показываем главное меню
                break;

            case "🏋️ Exercises":
                showExercisesMenu(chatId);  // Показываем меню упражнений
                break;

            case "📋 Workout Templates":
                showTemplatesMenu(chatId);  // Показываем меню шаблонов
                break;

            case "📝 All Exercises":
                showAllExercisesWithSelection(chatId);  // Показываем упражнения с inline кнопками
                break;
            case "➕ Create Exercise":
                createExercise(chatId);
                break;
            case "💪 By Muscle Group":
                showMuscleGroupsMenu(chatId);  // Показываем меню групп мышц
                break;

            case "📋 All Templates":
                showAllTemplates(chatId);  // Показываем шаблоны тренировок
                break;

            case "▶️ Start Workout":
                startWorkout(chatId);  // Начинаем тренировку
                break;
            case "\uD83D\uDCCA Progress":
                featureInProgress(chatId);
                break;
            case "ℹ\uFE0F Help":
                featureInProgress(chatId);
                break;
            default:
                showMainMenu(chatId);  // По умолчанию показываем главное меню
                break;
        }
    }

    private void featureInProgress(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(),
                "Данный функционал находится в разработке");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void createExercise(Long chatId) {
        String message = "Напишите упражнение";
        SendMessage sendMessage = new SendMessage(chatId.toString(), message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод для показа главного меню
    private void showMainMenu(Long chatId) {
        String welcomeText = """
                🏋️‍♂️ Добро пожаловать в WolfGym Bot!
                
                Выберите раздел:
                • 🏋️ Exercises - управление упражнениями
                • 📋 Workout Templates - шаблоны тренировок
                • 📊 Progress - ваш прогресс
                • ℹ️ Help - справка
                """;

        SendMessage message = new SendMessage(chatId.toString(), welcomeText);
        message.setReplyMarkup(KeyboardFactory.createMainMenu()); // Устанавливаем главное меню

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод для показа меню упражнений
    private void showExercisesMenu(Long chatId) {
        String exercisesText = """
                🏋️ Управление упражнениями
                
                Выберите действие:
                • 📝 All Exercises - все упражнения
                • ➕ Create Exercise - создать новое
                • 💪 By Muscle Group - по группе мышц
                • 🔍 Search Exercise - поиск упражнения
                """;

        SendMessage message = new SendMessage(chatId.toString(), exercisesText);
        message.setReplyMarkup(KeyboardFactory.createExercisesMenu()); // Устанавливаем меню упражнений

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Метод для показа меню шаблонов
    private void showTemplatesMenu(Long chatId) {
        String templatesText = """
                📋 Управление шаблонами тренировок
                
                Выберите действие:
                • 📋 All Templates - все шаблоны
                • 🆕 Create Template - создать новый
                • ▶️ Start Workout - начать тренировку
                • 📊 My Workouts - мои тренировки
                """;

        SendMessage message = new SendMessage(chatId.toString(), templatesText);
        message.setReplyMarkup(KeyboardFactory.createTemplatesMenu()); // Устанавливаем меню шаблонов

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Заглушки для будущей реализации
    private void showMuscleGroupsMenu(Long chatId) {
        String messageText = """
                💪 <b>Выберите группу мышц</b>
                
                Я покажу все упражнения для выбранной группы мышц.
                Просто нажмите на нужную группу ниже 👇
                """;

        SendMessage message = new SendMessage(chatId.toString(), messageText);
        message.setParseMode("HTML");
        message.setReplyMarkup(KeyboardFactory.createMuscleGroupsKeyboard());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showAllTemplates(Long chatId) {
        try {
            // Получаем все упражнения через сервис
            var templates = workoutTemplateService.getAllTemplates();
            if (templates.isEmpty()) {
                sendTextMessage(chatId, "📝 Шаблонов пока нет");
                return;
            }

            // Форматируем сообщение со списком
            String messageText = "🏋️ Выберите шаблон для просмотра:\n\n";

            SendMessage message = new SendMessage(chatId.toString(), messageText);
            message.setReplyMarkup(KeyboardFactory.createWorkoutTemplatesInlineKeyboard(templates));

            execute(message);  // Отправляем сообщение с inline кнопками

        } catch (Exception e) {
            sendTextMessage(chatId, "❌ Ошибка при загрузке шаблонов");
            e.printStackTrace();
        }
    }

    private void startWorkout(Long chatId) {
        sendTextMessage(chatId, "▶️ Функция начала тренировки скоро будет доступна!");
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        if (callbackData.startsWith("exercise_select_")) {
            String exerciseName = callbackData.substring("exercise_select_".length());
            showExerciseDetails(chatId, messageId, exerciseName);
        }
        // поиск по группе мышц
        else if (callbackData.startsWith("muscle_group_")) {
            String muscleGroup = callbackData.substring(13); // получаем группу мышц
            List<ExerciseDTO> exerciseByMuscleGroupList = exerciseService.getExercisesByMuscleGroup(muscleGroup);
            showAllExercisesByMuscleGroupWithSelection(chatId, exerciseByMuscleGroupList);            
        }
        // Обрабатываем кнопку "Назад к списку"
        else if ("exercise_back".equals(callbackData)) {
            // Удаляем сообщение с inline кнопками
            deleteMessage(chatId, messageId);
            // Показываем меню упражнений
            showExercisesMenu(chatId);
        }
    }

    private void showAllExercisesByMuscleGroupWithSelection(Long chatId, List<ExerciseDTO> list) {
        try {
            if (list.isEmpty()) {
                sendTextMessage(chatId, "📝 Упражнений пока нет");
                return;
            }

            // Форматируем сообщение со списком
            String messageText = "🏋️ Выберите упражнение для просмотра деталей:\n\n";

            SendMessage message = new SendMessage(chatId.toString(), messageText);
            // Устанавливаем inline клавиатуру с упражнениями
            message.setReplyMarkup(KeyboardFactory.createExercisesInlineKeyboard(list));

            execute(message);  // Отправляем сообщение с inline кнопками

        } catch (Exception e) {
            sendTextMessage(chatId, "❌ Ошибка при загрузке упражнений");
            e.printStackTrace();
        }
    }

    // Вспомогательный метод для удаления сообщения
    private void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage(chatId.toString(), messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void showAllExercisesWithSelection(Long chatId) {
        try {
            // Получаем все упражнения через сервис
            List<ExerciseDTO> exercises = exerciseService.getAllExercises();

            if (exercises.isEmpty()) {
                sendTextMessage(chatId, "📝 Упражнений пока нет");
                return;
            }

            // Форматируем сообщение со списком
            String messageText = "🏋️ Выберите упражнение для просмотра деталей:\n\n";

            SendMessage message = new SendMessage(chatId.toString(), messageText);
            // Устанавливаем inline клавиатуру с упражнениями
            message.setReplyMarkup(KeyboardFactory.createExercisesInlineKeyboard(exercises));

            execute(message);  // Отправляем сообщение с inline кнопками

        } catch (Exception e) {
            sendTextMessage(chatId, "❌ Ошибка при загрузке упражнений");
            e.printStackTrace();
        }
    }

    private void showExerciseDetails(Long chatId, Integer messageId, String exerciseName) {
        try {
            // Получаем упражнение по названию через сервис
            ExerciseDTO exercise = exerciseService.getExerciseByName(exerciseName);

            if (exercise == null) {
                // Если упражнение не найдено, показываем сообщение
                EditMessageText errorMessage = new EditMessageText();
                errorMessage.setChatId(chatId.toString());
                errorMessage.setMessageId(messageId);
                errorMessage.setText("❌ Упражнение не найдено: " + exerciseName);
                execute(errorMessage);
                return;
            }

            // Форматируем детальную информацию об упражнении
            String exerciseDetails = formatExerciseDetails(exercise);

            EditMessageText editMessage = new EditMessageText();
            editMessage.setChatId(chatId.toString());      // Устанавливаем ID чата
            editMessage.setMessageId(messageId);           // Устанавливаем ID сообщения
            editMessage.setText(exerciseDetails);          // Устанавливаем новый текст
            editMessage.setParseMode("HTML");              // Включаем HTML разметку

            execute(editMessage);  // Редактируем сообщение

        } catch (Exception e) {
            sendTextMessage(chatId, "❌ Ошибка при загрузке информации об упражнении");  // Сообщение об ошибке
            e.printStackTrace();  // Логируем ошибку
        }
    }

    // Отправляем красивое сообщение упражнения
    private String formatExerciseDetails(ExerciseDTO exercise) {
        StringBuilder sb = new StringBuilder();
        sb.append("🏋️ <b>").append(exercise.getName()).append("</b>\n\n");  // Название упражнения

        if (exercise.getDescription() != null && !exercise.getDescription().isEmpty()) {
            sb.append("📝 ").append(exercise.getDescription()).append("\n\n");  // Описание
        }

        if (exercise.getMuscleGroup() != null) {
            sb.append("💪 Группа мышц: ").append(exercise.getMuscleGroup()).append("\n");  // Группа мышц
        }

        if (exercise.getVideoUrl() != null && !exercise.getVideoUrl().isEmpty()) {
            sb.append("🎥 Видео: ").append(exercise.getVideoUrl()).append("\n");  // Ссылка на видео
        }

        return sb.toString();  // Возвращаем отформатированную строку
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);  // Создаем сообщение
        try {
            execute(message);  // Отправляем сообщение
        } catch (TelegramApiException e) {
            e.printStackTrace();  // Логируем ошибку
        }
    }

    @Override
    public String getBotUsername() {
        return "WolfGymBot";
    }
}
