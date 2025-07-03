package org.example.frontendToolsInProduction;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.frontendToolsInProduction.model.AvailableTool;
import org.example.frontendToolsInProduction.model.HistoryWriteOff;
import org.example.frontendToolsInProduction.model.Tools;

public class ProceduresWindowController {
    private String token;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private ObservableList<Object> tableData = FXCollections.observableArrayList(); // Для хранения Tools или HistoryWriteOff

    @FXML private ImageView backIcon;
    @FXML private TableView<Object> tableView;
    @FXML private Label getAvailableToolsInStorageLabel;
    @FXML private Label getToolsInUseByEmployeeLabel;
    @FXML private Label addToolLabel;
    @FXML private Label addToolBatchLabel;
    @FXML private Label writeOffToolLabel;
    @FXML private Button getAvailableToolsInStorage;
    @FXML private Button getToolsInUseByEmployee;
    @FXML private Button addTool;
    @FXML private Button addToolBatch;
    @FXML private Button writeOffTool;
    @FXML private TextField storageIdGet;
    @FXML private TextField employeeId;
    @FXML private TextField toolTypeIdAddOne;
    @FXML private TextField storageIdAddOne;
    @FXML private TextField toolTypeIdAddBatch;
    @FXML private TextField storageIdAddBatch;
    @FXML private TextField count;
    @FXML private TextField toolId;

    public void setToken(String token) {
        this.token = token;
    }

    @FXML
    private void initialize() {
        this.backIcon.setOnMouseClicked((event) -> this.openMainWindow());
        tableView.setItems(tableData);

        restrictToEnglishLettersAndDigits(storageIdGet);
        restrictToEnglishLettersAndDigits(employeeId);
        restrictToEnglishLettersAndDigits(toolTypeIdAddOne);
        restrictToEnglishLettersAndDigits(storageIdAddOne);
        restrictToEnglishLettersAndDigits(toolTypeIdAddBatch);
        restrictToEnglishLettersAndDigits(storageIdAddBatch);
        restrictToEnglishLettersAndDigits(toolId);
        restrictToIntegers(count);

        clear();
    }

    private void restrictToEnglishLettersAndDigits(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9-]*")) {
                field.setText(newValue.replaceAll("[^a-zA-Z0-9-]", ""));
            }
        });
    }

    private void restrictToIntegers(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void clear() {
        // Скрытие кнопок
        getAvailableToolsInStorage.setVisible(false);
        getAvailableToolsInStorage.setManaged(false);
        getToolsInUseByEmployee.setVisible(false);
        getToolsInUseByEmployee.setManaged(false);
        addTool.setVisible(false);
        addTool.setManaged(false);
        addToolBatch.setVisible(false);
        addToolBatch.setManaged(false);
        writeOffTool.setVisible(false);
        writeOffTool.setManaged(false);

        // Скрытие текстовых полей
        storageIdGet.setVisible(false);
        storageIdGet.setManaged(false);
        storageIdGet.setText("");
        employeeId.setVisible(false);
        employeeId.setManaged(false);
        employeeId.setText("");
        toolTypeIdAddOne.setVisible(false);
        toolTypeIdAddOne.setManaged(false);
        toolTypeIdAddOne.setText("");
        storageIdAddOne.setVisible(false);
        storageIdAddOne.setManaged(false);
        storageIdAddOne.setText("");
        toolTypeIdAddBatch.setVisible(false);
        toolTypeIdAddBatch.setManaged(false);
        toolTypeIdAddBatch.setText("");
        storageIdAddBatch.setVisible(false);
        storageIdAddBatch.setManaged(false);
        storageIdAddBatch.setText("");
        count.setVisible(false);
        count.setManaged(false);
        count.setText("");
        toolId.setVisible(false);
        toolId.setManaged(false);
        toolId.setText("");
    }

    @FXML
    private void onClickAvailableToolsInStorageLabel() {
        boolean currentlyVisible = getAvailableToolsInStorage.isVisible();
        clear();
        storageIdGet.setVisible(!currentlyVisible);
        storageIdGet.setManaged(!currentlyVisible);
        getAvailableToolsInStorage.setVisible(!currentlyVisible);
        getAvailableToolsInStorage.setManaged(!currentlyVisible);
    }

    @FXML
    private void onClickToolsInUseByEmployeeLabel() {
        boolean currentlyVisible = getToolsInUseByEmployee.isVisible();
        clear();
        employeeId.setVisible(!currentlyVisible);
        employeeId.setManaged(!currentlyVisible);
        getToolsInUseByEmployee.setVisible(!currentlyVisible);
        getToolsInUseByEmployee.setManaged(!currentlyVisible);
    }

    @FXML
    private void onClickAddToolLabel() {
        boolean currentlyVisible = addTool.isVisible();
        clear();
        toolTypeIdAddOne.setVisible(!currentlyVisible);
        toolTypeIdAddOne.setManaged(!currentlyVisible);
        storageIdAddOne.setVisible(!currentlyVisible);
        storageIdAddOne.setManaged(!currentlyVisible);
        addTool.setVisible(!currentlyVisible);
        addTool.setManaged(!currentlyVisible);
    }

    @FXML
    private void onClickAddToolBatchLabel() {
        boolean currentlyVisible = addToolBatch.isVisible();
        clear();
        toolTypeIdAddBatch.setVisible(!currentlyVisible);
        toolTypeIdAddBatch.setManaged(!currentlyVisible);
        storageIdAddBatch.setVisible(!currentlyVisible);
        storageIdAddBatch.setManaged(!currentlyVisible);
        count.setVisible(!currentlyVisible);
        count.setManaged(!currentlyVisible);
        addToolBatch.setVisible(!currentlyVisible);
        addToolBatch.setManaged(!currentlyVisible);
    }

    @FXML
    private void onClickWriteOffToolLabel() {
        boolean currentlyVisible = writeOffTool.isVisible();
        clear();
        toolId.setVisible(!currentlyVisible);
        toolId.setManaged(!currentlyVisible);
        writeOffTool.setVisible(!currentlyVisible);
        writeOffTool.setManaged(!currentlyVisible);
    }

    @FXML
    private void onClickAvailableToolsInStorage() {
        String idPlace = storageIdGet.getText().toUpperCase();
        if (idPlace.isEmpty()) {
            showAlert("Ошибка", "Введите код места хранения");
            return;
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/tools/available/" + idPlace))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();
            // ПРОКОММЕНТИРОВАТЬ
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        handleResponse(response, true, "availableTools");
                    }))
                    .exceptionally(e -> {
                        Platform.runLater(() -> showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage()));
                        return null;
                    });
        }
        catch (Exception e) {
            showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage());
        }
    }

    @FXML
    private void onClickToolsInUseByEmployee() {
        String idEmployee = employeeId.getText().toUpperCase();
        if (idEmployee.isEmpty()) {
            showAlert("Ошибка", "Введите код сотрудника");
            return;
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/history-tool-issues/tools-in-use-by-employee/" + idEmployee))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();
            // ПРОКОММЕНТИРОВАТЬ
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        handleResponse(response, true, "toolsInUse");
                    }))
                    .exceptionally(e -> {
                        Platform.runLater(() -> showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage()));
                        return null;
                    });
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage());
        }
    }

    @FXML
    private void onClickAddTool() {
        String articleToolType = toolTypeIdAddOne.getText().toUpperCase();
        String idPlace = storageIdAddOne.getText().toUpperCase();
        if (articleToolType.isEmpty() || idPlace.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля");
            return;
        }
        try {
            String body = "articleToolType=" + articleToolType + "&idPlace=" + idPlace;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/tools"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        handleResponse(response, false, "tools");
                        if (response.statusCode() == 201 || response.statusCode() == 200) {
                            fetchAllTools(articleToolType, idPlace); // Обновление таблицы с инструментами
                        }
                    }))
                    .exceptionally(e -> {
                        Platform.runLater(() -> showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage()));
                        return null;
                    });
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage());
        }
    }

    @FXML
    private void onClickAddToolBatch() {
        String articleToolType = toolTypeIdAddBatch.getText().toUpperCase();
        String idPlace = storageIdAddBatch.getText().toUpperCase();
        String toolCount = count.getText();
        if (articleToolType.isEmpty() || idPlace.isEmpty() || toolCount.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля");
            return;
        }
        try {
            String body = "articleToolType=" + articleToolType + "&idPlace=" + idPlace + "&toolCount=" + toolCount;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/tools/batch"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        handleResponse(response, false, "tools");
                        if (response.statusCode() == 201 || response.statusCode() == 200) {
                            fetchAllTools(articleToolType, idPlace); // Обновление таблицы с инструментами
                        }
                    }))
                    .exceptionally(e -> {
                        Platform.runLater(() -> showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage()));
                        return null;
                    });
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage());
        }
    }

    @FXML
    private void onClickWriteOffTool() {
        String idToolValue = toolId.getText().toUpperCase();
        if (idToolValue.isEmpty()) {
            showAlert("Ошибка", "Введите код инструмента");
            return;
        }
        try {
            String body = "idTool=" + idToolValue;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/history-write-off-instruments"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        handleResponse(response, false, "writeOff");
                        if (response.statusCode() == 201 || response.statusCode() == 200) {
                            fetchWriteOffs(idToolValue); // Обновление таблицы со списаниями
                        }
                    }))
                    .exceptionally(e -> {
                        Platform.runLater(() -> showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage()));
                        return null;
                    });
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось выполнить запрос: " + e.getMessage());
        }
    }

    private void handleResponse(HttpResponse<String> response, boolean isGetRequest, String dataType) {
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            tableData.clear();
            tableView.getColumns().clear();

            if (isGetRequest) {
                JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class); // Ожидается массив
                if (jsonArray != null) {
                    if (dataType.equals("tools")) {
                        // Настройка столбцов для tools
                        setupToolsColumns();
                        for (var element : jsonArray) {
                            try {
                                Tools tool = gson.fromJson(element, Tools.class);
                                tableData.add(tool);
                            } catch (Exception e) {
                                showAlert("Ошибка", "Ошибка парсинга инструмента: " + e.getMessage() + ". Ответ: " + element);
                            }
                        }
                        // Сортировка по времени
                        tableData.sort((o1, o2) -> {
                            String t1 = ((Tools) o1).getDateTimeAdmission();
                            String t2 = ((Tools) o2).getDateTimeAdmission();
                            return t2.compareTo(t1); // От новых к старым
                        });
                    }
                    else if (dataType.equals("writeOff")) {
                        // Настройка столбцов для historyWriteOff
                        setupWriteOffColumns();
                        for (var element : jsonArray) {
                            try {
                                HistoryWriteOff writeOff = gson.fromJson(element, HistoryWriteOff.class);
                                tableData.add(writeOff);
                            } catch (Exception e) {
                                showAlert("Ошибка", "Ошибка парсинга списания: " + e.getMessage() + ". Ответ: " + element);
                            }
                        }
                        // Сортировка по времени
                        tableData.sort((o1, o2) -> {
                            String t1 = ((HistoryWriteOff) o1).getDateTimeWriteOff();
                            String t2 = ((HistoryWriteOff) o2).getDateTimeWriteOff();
                            return t2.compareTo(t1); // От новых к старым
                        });
                    }
                    else if (dataType.equals("availableTools") || dataType.equals("toolsInUse")) {
                        // Настройка столбцов для tools
                        if (dataType.equals("availableTools")) setupAvailableToolsColumns();
                        else setupToolsInUseColumns();
                        for (var element : jsonArray) {
                            JsonArray jsonValues = element.getAsJsonArray();
                            AvailableTool tool = new AvailableTool();
                            tool.setIdTool(jsonValues.get(0).getAsString());
                            tool.setToolTypeName(jsonValues.get(1).getAsString());
                            tool.setDeliveryDate(jsonValues.get(2).getAsString());
                            tableData.add(tool);
                        }
                        // Сортировка по времени
                        tableData.sort((o1, o2) -> {
                            String t1 = ((AvailableTool) o1).getDeliveryDate();
                            String t2 = ((AvailableTool) o2).getDeliveryDate();
                            return t2.compareTo(t1); // От новых к старым
                        });
                    }
                }
                else {
                    showAlert("Ошибка", "Невозможно распознать данные как массив или объект с полем 'data'. Ответ: " + response.body());
                }
            } else {
                // Обработка ответа для POST-запросов
                String message;
                try {
                    JsonObject responseObj = gson.fromJson(response.body(), JsonObject.class);
                    message = responseObj != null && responseObj.has("message")
                            ? responseObj.get("message").getAsString()
                            : "Запрос выполнен успешно";
                } catch (Exception e) {
                    // Если не объект, используем тело ответа как сообщение
                    message = response.body().isEmpty() ? "Запрос выполнен успешно" : response.body();
                }
                showAlert("Успех", message);
            }
        }
        else {
            String errorMessage;
            switch (response.statusCode()) {
                case 400:
                    errorMessage = "Неверные параметры запроса. Проверьте введенные данные.";
                    break;
                case 401:
                    errorMessage = "Токен недействителен. Пожалуйста, войдите снова.";
                    break;
                case 403:
                    errorMessage = "У вас нет прав для выполнения этой операции.";
                    break;
                case 404:
                    errorMessage = "Запрашиваемый ресурс не найден.";
                    break;
                case 500:
                    errorMessage = "Внутренняя ошибка сервера. Попробуйте позже.";
                    break;
                default:
                    errorMessage = "Ошибка сервера: " + response.statusCode();
            }
            showAlert("Ошибка", errorMessage);
        }
    }

    // Получение списка всех инструментов
    private void fetchAllTools(String articleToolType, String idPlace) {
        try {
            String query = "";
            if (!articleToolType.isEmpty() && !idPlace.isEmpty()) {
                query = "?articleToolType=" + articleToolType + "&idPlace=" + idPlace;
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/tools" + query))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        handleResponse(response, true, "tools");
                    }))
                    .exceptionally(e -> {
                        Platform.runLater(() -> showAlert("Ошибка", "Не удалось загрузить инструменты: " + e.getMessage()));
                        return null;
                    });
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось загрузить инструменты: " + e.getMessage());
        }
    }

    // Получение списка всех списаний
    private void fetchWriteOffs(String idTool) {
        try {
            String query = idTool.isEmpty() ? "" : "?idTool=" + idTool;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/history-write-off-instruments" + query))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() -> {
                        handleResponse(response, true, "writeOff");
                    }))
                    .exceptionally(e -> {
                        Platform.runLater(() -> showAlert("Ошибка", "Не удалось загрузить списания: " + e.getMessage()));
                        return null;
                    });
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось загрузить списания: " + e.getMessage());
        }
    }

    private void setupToolsColumns() {
        TableColumn<Object, String> idToolCol = new TableColumn<>("ID инструмента");
        idToolCol.setPrefWidth(150);
        idToolCol.setCellValueFactory(cellData -> {
            Tools tool = (Tools) cellData.getValue();
            return new SimpleStringProperty(tool.getIdTool());
        });

        TableColumn<Object, String> articleToolTypeCol = new TableColumn<>("Артикул");
        articleToolTypeCol.setPrefWidth(150);
        articleToolTypeCol.setCellValueFactory(cellData -> {
            Tools tool = (Tools) cellData.getValue();
            return new SimpleStringProperty(tool.getTypeOfTool());
        });

        TableColumn<Object, String> idPlaceCol = new TableColumn<>("Место хранения");
        idPlaceCol.setPrefWidth(150);
        idPlaceCol.setCellValueFactory(cellData -> {
            Tools tool = (Tools) cellData.getValue();
            return new SimpleStringProperty(tool.getStorageLocation());
        });

        TableColumn<Object, String> availabilityCol = new TableColumn<>("Доступность");
        availabilityCol.setPrefWidth(100);
        availabilityCol.setCellValueFactory(cellData -> {
            Tools tool = (Tools) cellData.getValue();
            return new SimpleStringProperty(tool.isAvailability() ? "Да" : "Нет");
        });

        TableColumn<Object, String> timestampCol = new TableColumn<>("Время поступления");
        timestampCol.setPrefWidth(200);
        timestampCol.setCellValueFactory(cellData -> {
            Tools tool = (Tools) cellData.getValue();
            return new SimpleStringProperty(tool.getDateTimeAdmission());
        });

        tableView.getColumns().setAll(idToolCol, articleToolTypeCol, idPlaceCol, availabilityCol, timestampCol);
    }

    private void setupWriteOffColumns() {
        TableColumn<Object, String> idWriteOffCol = new TableColumn<>("ID списания");
        idWriteOffCol.setPrefWidth(150);
        idWriteOffCol.setCellValueFactory(cellData -> {
            HistoryWriteOff writeOff = (HistoryWriteOff) cellData.getValue();
            return new SimpleStringProperty(writeOff.getIdWriteOff());
        });

        TableColumn<Object, String> idToolCol = new TableColumn<>("ID инструмента");
        idToolCol.setPrefWidth(150);
        idToolCol.setCellValueFactory(cellData -> {
            HistoryWriteOff writeOff = (HistoryWriteOff) cellData.getValue();
            return new SimpleStringProperty(writeOff.getIdTool());
        });

        TableColumn<Object, String> nameCol = new TableColumn<>("Причина");
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(cellData -> {
            HistoryWriteOff writeOff = (HistoryWriteOff) cellData.getValue();
            return new SimpleStringProperty(writeOff.getName());
        });

        TableColumn<Object, String> timestampCol = new TableColumn<>("Время списания");
        timestampCol.setPrefWidth(200);
        timestampCol.setCellValueFactory(cellData -> {
            HistoryWriteOff writeOff = (HistoryWriteOff) cellData.getValue();
            return new SimpleStringProperty(writeOff.getDateTimeWriteOff());
        });

        tableView.getColumns().setAll(idWriteOffCol, idToolCol, nameCol, timestampCol);
    }

    private void setupAvailableToolsColumns() {
        TableColumn<Object, String> idToolCol = new TableColumn<>("ID инструмента");
        idToolCol.setPrefWidth(150);
        idToolCol.setCellValueFactory(cellData -> {
            AvailableTool tool = (AvailableTool) cellData.getValue();
            return new SimpleStringProperty(tool.getIdTool());
        });

        TableColumn<Object, String> toolTypeNameCol = new TableColumn<>("Название типа инструмента");
        toolTypeNameCol.setPrefWidth(200);
        toolTypeNameCol.setCellValueFactory(cellData -> {
            AvailableTool tool = (AvailableTool) cellData.getValue();
            return new SimpleStringProperty(tool.getToolTypeName());
        });

        TableColumn<Object, String> deliveryDateCol = new TableColumn<>("Дата поступления");
        deliveryDateCol.setPrefWidth(200);
        deliveryDateCol.setCellValueFactory(cellData -> {
            AvailableTool tool = (AvailableTool) cellData.getValue();
            return new SimpleStringProperty(tool.getDeliveryDate());
        });

        tableView.getColumns().setAll(idToolCol, toolTypeNameCol, deliveryDateCol);
    }

    private void setupToolsInUseColumns() {
        TableColumn<Object, String> idToolCol = new TableColumn<>("ID инструмента");
        idToolCol.setPrefWidth(150);
        idToolCol.setCellValueFactory(cellData -> {
            AvailableTool tool = (AvailableTool) cellData.getValue();
            return new SimpleStringProperty(tool.getIdTool());
        });

        TableColumn<Object, String> toolTypeNameCol = new TableColumn<>("Название типа инструмента");
        toolTypeNameCol.setPrefWidth(200);
        toolTypeNameCol.setCellValueFactory(cellData -> {
            AvailableTool tool = (AvailableTool) cellData.getValue();
            return new SimpleStringProperty(tool.getToolTypeName());
        });

        TableColumn<Object, String> issuanceDateCol = new TableColumn<>("Дата выдачи");
        issuanceDateCol.setPrefWidth(200);
        issuanceDateCol.setCellValueFactory(cellData -> {
            AvailableTool tool = (AvailableTool) cellData.getValue();
            return new SimpleStringProperty(tool.getDeliveryDate());
        });

        tableView.getColumns().setAll(idToolCol, toolTypeNameCol, issuanceDateCol);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void openMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("mainWindow.fxml"));
            Scene mainScene = new Scene((Parent)loader.load(), (double)1100.0F, (double)600.0F);
            Stage mainStage = new Stage();
            mainStage.setTitle("Склад");
            mainStage.setScene(mainScene);
            mainStage.centerOnScreen();
            mainStage.show();
            MainWindowController mainController = (MainWindowController)loader.getController();
            mainController.setToken(this.token);
            ((Stage)this.backIcon.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}