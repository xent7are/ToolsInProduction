package org.example.frontedToolsInProduction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AuthorizationController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button entryButton;

    private String jwtToken;

    @FXML
    public void initialize() {
        // Отключение фокуса на старте
        loginField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);

        // Обработка кнопки
        entryButton.setOnAction(event -> handleLoginButton());
    }

    private void handleLoginButton() {
        // Получение текста из полей ввода и убирание пробелы по краям
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        // Проверка, что логин и пароль не пустые
        if (login.isEmpty() || password.isEmpty()) {
            showErrorAlert("Пожалуйста, введите логин и пароль.");
            return;
        }

        // Формирование данных для POST-запроса, кодирование значения
        String postData = "login=" + encodeValue(login) + "&password=" + encodeValue(password);

        try {
            // Создание URL для запроса к серверу авторизации
            URL url = new URL("http://localhost:8080/auth/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true); // Разрешаем отправку данных в теле запроса

            // Отправка данных на сервер через поток вывода
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Получение кода ответа от сервера
            int responseCode = conn.getResponseCode();
            StringBuilder response = new StringBuilder();

            // Считывание ответ сервера
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            conn.disconnect();

            // Обрабатка ответа в зависимости от кода
            if (responseCode == 200) {
                Gson gson = new Gson();
                Map<String, String> responseMap = gson.fromJson(response.toString(), new TypeToken<Map<String, String>>() {}.getType());
                jwtToken = responseMap.get("token"); // Получение JWT токен из ответа

                if (jwtToken == null || jwtToken.isEmpty()) {
                    showErrorAlert("Токен не найден в ответе сервера.");
                    return;
                }


                openMainWindow();

            } else {
                showErrorAlert("Ошибка авторизации: " + response.toString());
            }

        } catch (Exception e) {
            showErrorAlert("Ошибка подключения к серверу:\n" + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void openMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
            Scene mainScene = new Scene(loader.load(), 1350, 640);
            Stage mainStage = new Stage();
            mainStage.setTitle("База данных");
            mainStage.setScene(mainScene);
            mainStage.centerOnScreen();
            mainStage.show();
            MainWindowController mainController = loader.getController();
            mainController.setToken(jwtToken);
            ((Stage) entryButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для кодирования значений в URL-формате (замена пробелов)
    private String encodeValue(String value) {
        return value.replace(" ", "%20");
    }
}
