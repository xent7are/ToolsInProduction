package org.example.frontendToolsInProduction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.frontendToolsInProduction.model.*;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MainWindowController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private ComboBox<String> tableComboBox;

    @FXML
    private Label tableName;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private  Button deleteButton;

    @FXML
    private ImageView backIcon;

    @FXML
    private Button proceduresButton;

    private String token;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupComboBox();
        addButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        backIcon.setOnMouseClicked(event -> openAuthorizationWindow());
        proceduresButton.setOnMouseClicked(event -> this.openProcedures());

        tableComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            tableView.getItems().clear();
            tableView.getColumns().clear();

            if ("Инструменты".equals(newVal)) {
                setupToolColumns();
                loadToolsData();
                tableName.setText("Таблица Инструменты");
            } else if ("Места хранения".equals(newVal)) {
                setupStorageLocationColumns();
                loadStorageLocationData();
                tableName.setText("Таблица Места хранения");
            } else if ("Типы инструментов".equals(newVal)) {
                setupTypeOfToolColumns();
                loadTypeOfToolData();
                tableName.setText("Типы инструментов");
            } else if ("Должности".equals(newVal)) {
                setupPositionsColumns();
                loadPositionsData();
                tableName.setText("Таблица Должности");
            } else if ("История списаний инструментов".equals(newVal)) {
                setupHistoryWriteOffColumns();
                loadHistoryWriteOffData();
                tableName.setText("Таблица История списаний инструментов");
            } else if ("Сотрудники".equals(newVal)){
                setupEmployeesColumns();
                loadEmployeesData();
                tableName.setText("Таблица Сотрудники");
            } else if ("История выдачи инструментов".equals(newVal)){
                setupHistoryToolIssueColumns();
                loadHistoryToolIssue();
                tableName.setText("Таблица История выдачи инструментов");
            } else if ("Информация об инструментах".equals(newVal)){
                setupToolInfo();
                loadToolInfo();
                tableName.setText("Таблица Информация об инструментах");
            } else if ("Информация об инструментах у рабочих".equals(newVal)){
                setupEmployeeToolInfo();
                loadEmployeeToolInfo();
                tableName.setText("Таблица Информация об инструментах у рабочих");
            }
        });
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Установка значений для комбобокса
    private void setupComboBox() {
        tableComboBox.setItems(FXCollections.observableArrayList(
                "Сотрудники", "История выдачи инструментов", "История списаний инструментов",
                "Должности", "Места хранения", "Инструменты", "Типы инструментов", "Информация об инструментах",
                "Информация об инструментах у рабочих"
        ));
    }

    // Настройка столбцов таблицы для отображения свойств объектов Tools
    private void setupToolColumns() {
        TableColumn<Tools, String> idToolCol = new TableColumn<>("ID");
        idToolCol.setCellValueFactory(new PropertyValueFactory<>("idTool"));

        TableColumn<Tools, String> articleCol = new TableColumn<>("Тип");
        articleCol.setCellValueFactory(new PropertyValueFactory<>("typeOfTool"));

        TableColumn<Tools, String> placeCol = new TableColumn<>("Место");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("storageLocation"));

        TableColumn<Tools, String> availabilityCol = new TableColumn<>("Доступность");
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));

        TableColumn<Tools, String> dateCol = new TableColumn<>("Дата поступления");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTimeAdmission"));

        tableView.getColumns().addAll(idToolCol, articleCol, placeCol, availabilityCol, dateCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов StorageLocation
    private void setupStorageLocationColumns() {
        TableColumn<StorageLocation, String> idPlaceCol = new TableColumn<>("ID");
        idPlaceCol.setCellValueFactory(new PropertyValueFactory<>("idPlace"));

        TableColumn<StorageLocation, String> nameCol = new TableColumn<>("Название");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<StorageLocation, String> descCol = new TableColumn<>("Описание");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableView.getColumns().addAll(idPlaceCol, nameCol, descCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов TypeOfTool
    private void setupTypeOfToolColumns() {
        TableColumn<TypeOfTool, String> articleTypeCol = new TableColumn<>("Тип артикула инструмента");
        articleTypeCol.setCellValueFactory(new PropertyValueFactory<>("articleToolType"));

        TableColumn<TypeOfTool, String> nameCol = new TableColumn<>("Название");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TypeOfTool, String> descCol = new TableColumn<>("Описание");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableView.getColumns().addAll(articleTypeCol, nameCol, descCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов Positions
    private void setupPositionsColumns() {
        TableColumn<Positions, String> idPositionCol = new TableColumn<>("ID Должности");
        idPositionCol.setCellValueFactory(new PropertyValueFactory<>("idPosition"));

        TableColumn<Positions, String> titleCol = new TableColumn<>("Наименование должности");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("titlePosition"));

        TableColumn<Positions, String> reqCol = new TableColumn<>("Требования");
        reqCol.setCellValueFactory(new PropertyValueFactory<>("requirements"));

        TableColumn<Positions, String> dutiesCol = new TableColumn<>("Обязанности");
        dutiesCol.setCellValueFactory(new PropertyValueFactory<>("duties"));

        TableColumn<Positions, String> salaryCol = new TableColumn<>("Оклад");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Positions, String> roleCol = new TableColumn<>("Роль");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableView.getColumns().addAll(idPositionCol, titleCol, reqCol, dutiesCol, salaryCol, roleCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов HistoryWriteOff
    private void setupHistoryWriteOffColumns() {
        TableColumn<HistoryWriteOff, String> idWriteOffCol = new TableColumn<>("ID Списания");
        idWriteOffCol.setCellValueFactory(new PropertyValueFactory<>("idWriteOff"));

        TableColumn<HistoryWriteOff, String> idToolCol = new TableColumn<>("ID Инструмента");
        idToolCol.setCellValueFactory(new PropertyValueFactory<>("idTool"));

        TableColumn<HistoryWriteOff, String> nameCol = new TableColumn<>("Название");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<HistoryWriteOff, String> dateCol = new TableColumn<>("Дата списания");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTimeWriteOff"));

        tableView.getColumns().addAll(idWriteOffCol, idToolCol, nameCol, dateCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов Employees
    private void setupEmployeesColumns() {
        TableColumn<Employees, String> idEmployeeCol = new TableColumn<>("ID сотрудника");
        idEmployeeCol.setCellValueFactory(new PropertyValueFactory<>("idEmployee"));

        TableColumn<Employees, String> idPositionCol = new TableColumn<>("ID должности");
        idPositionCol.setCellValueFactory(new PropertyValueFactory<>("idPosition"));

        TableColumn<Employees, String> surnameCol = new TableColumn<>("Фамилия");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Employees, String> nameCol = new TableColumn<>("Имя");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employees, String> patronymicCol = new TableColumn<>("Отчество");
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));

        TableColumn<Employees, String> phoneCol = new TableColumn<>("Телефон");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Employees, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Employees, String> loginCol = new TableColumn<>("Логин");
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<Employees, String> passwordCol = new TableColumn<>("Пароль");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableView.getColumns().addAll(idEmployeeCol, idPositionCol, surnameCol, nameCol,
                patronymicCol, phoneCol, emailCol, loginCol, passwordCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов HistoryToolIssue
    private void setupHistoryToolIssueColumns() {
        TableColumn<HistoryToolIssue, String> employeeCol = new TableColumn<>("ID Сотрудника");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>("idEmployee"));

        TableColumn<HistoryToolIssue, String> toolCol = new TableColumn<>("ID Инструмента");
        toolCol.setCellValueFactory(new PropertyValueFactory<>("idTool"));

        TableColumn<HistoryToolIssue, String> actionCol = new TableColumn<>("Действие");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));

        TableColumn<HistoryToolIssue, String> dateCol = new TableColumn<>("Дата выдачи");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTimeIssue"));

        tableView.getColumns().addAll(employeeCol, toolCol, actionCol, dateCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов ToolInfo
    private void setupToolInfo() {
        TableColumn<ToolInfo, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idTool"));

        TableColumn<ToolInfo, String> nameCol = new TableColumn<>("Название");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ToolInfo, String> storageCol = new TableColumn<>("Место хранения");
        storageCol.setCellValueFactory(new PropertyValueFactory<>("storageLocation"));

        TableColumn<ToolInfo, Integer> availabilityCol = new TableColumn<>("Доступность");
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));

        TableColumn<ToolInfo, String> dateCol = new TableColumn<>("Дата поступления");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTimeAdmission"));

        tableView.getColumns().addAll(idCol, nameCol, storageCol, availabilityCol, dateCol);
    }

    // Настройка столбцов таблицы для отображения свойств объектов EmployeeToolInfo
    private void setupEmployeeToolInfo() {
        TableColumn<EmployeeToolInfo, String> idCol = new TableColumn<>("Идентификатор");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<EmployeeToolInfo, String> nameToolCol = new TableColumn<>("Наименование инструмента");
        nameToolCol.setCellValueFactory(new PropertyValueFactory<>("positionName"));

        TableColumn<EmployeeToolInfo, String> surnameCol = new TableColumn<>("Фамилия сотрудника");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<EmployeeToolInfo, Integer> nameCol = new TableColumn<>("Имя сотрудника");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<EmployeeToolInfo, String> patronymicCol = new TableColumn<>("Отчество сотрудника");
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));

        TableColumn<EmployeeToolInfo, String> nameJobCol = new TableColumn<>("Наименование должности");
        nameJobCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        TableColumn<EmployeeToolInfo, String> phoneCol = new TableColumn<>("Номер телефона сотрудника");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<EmployeeToolInfo, String> emailCol = new TableColumn<>("Email сотрудника");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<EmployeeToolInfo, String> admissionDateCol = new TableColumn<>("Дата выдачи");
        admissionDateCol.setCellValueFactory(new PropertyValueFactory<>("admissionDate"));

        tableView.getColumns().addAll(idCol, nameToolCol, surnameCol, nameCol, patronymicCol, nameJobCol, phoneCol, emailCol, admissionDateCol);
    }


    private void loadToolsData() {
        loadData("http://localhost:8080/tools", new TypeToken<List<Tools>>() {}, Tools.class);
    }

    private void loadStorageLocationData() {
        loadData("http://localhost:8080/storage-locations", new TypeToken<List<StorageLocation>>() {}, StorageLocation.class);
    }

    private void loadTypeOfToolData() {
        loadData("http://localhost:8080/types-of-tools", new TypeToken<List<TypeOfTool>>() {}, TypeOfTool.class);
    }

    private void loadPositionsData() {
        loadData("http://localhost:8080/positions", new TypeToken<List<Positions>>() {}, Positions.class);
    }

    private void loadHistoryWriteOffData() {
        loadData("http://localhost:8080/history-write-off-instruments", new TypeToken<List<HistoryWriteOff>>() {}, HistoryWriteOff.class);
    }

    private void loadEmployeesData() {
        loadData("http://localhost:8080/employees", new TypeToken<List<Employees>>() {}, Employees.class);
    }

    private void loadHistoryToolIssue() {
        loadData("http://localhost:8080/history-tool-issues", new TypeToken<List<HistoryToolIssue>>() {}, HistoryToolIssue.class);
    }

    private void loadToolInfo() {
        loadDataToolInfo("http://localhost:8080/tools/full-info");
    }

    private void loadEmployeeToolInfo() {
        loadDataEmployeeToolInfo("http://localhost:8080/history-tool-issues/tools-in-use");
    }


    private <T> void loadData(String apiUrl, TypeToken<List<T>> typeToken, Class<T> currentClass) {
        // Проверка, что токен авторизации установлен
        if (token == null || token.isEmpty()) {
            showAlert("Ошибка авторизации", "Токен не установлен. Данные не будут загружены.");
            clearTable();
            return;
        }

        clearTable();

        try {
            // Создание подключения к серверу по указанному URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Добавление в заголовок запроса токен авторизации
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode(); // Получение кода ответа от сервера

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Если ответ успешный, считывается поток данных
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                List<T> list = gson.fromJson(response.toString(), typeToken.getType());

                ObservableList<T> observableList = FXCollections.observableArrayList(list);

                setupTableColumns(currentClass);

                tableView.setItems(observableList);

                addButton.setDisable(false);
                editButton.setDisable(false);
                deleteButton.setDisable(false);

            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED || responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                showAlert("Ошибка доступа", "Доступ запрещён: недостаточно прав для просмотра данных.");
                clearTable();
                addButton.setDisable(true);
            } else {
                showAlert("Ошибка загрузки", "Код ответа: " + responseCode);
                clearTable();
                addButton.setDisable(true);
            }

        } catch (Exception e) {
            showAlert("Ошибка соединения", "Не удалось соединиться с сервером: " + e.getMessage());
            clearTable();
        }
    }


    private void loadDataToolInfo(String apiUrl) {
        // Проверка, что токен авторизации установлен
        if (token == null || token.isEmpty()) {
            showAlert("Ошибка авторизации", "Токен не установлен. Данные не будут загружены.");
            return;
        }

        try {
            // Создание подключения к серверу по указанному URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Добавление в заголовок запроса токен авторизации
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode(); // Получение кода ответа от сервера

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Если ответ успешный, считывается поток данных
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();

                List<List<Object>> rawList = gson.fromJson(response.toString(), new TypeToken<List<List<Object>>>() {}.getType());

                ObservableList<ToolInfo> newToolList = FXCollections.observableArrayList();
                for (List<Object> item : rawList) {
                    ToolInfo nt = new ToolInfo();
                    nt.setIdTool(item.get(0).toString());
                    nt.setName(item.get(1).toString());
                    nt.setStorageLocation(item.get(2).toString());
                    nt.setAvailability(item.get(3).toString());
                    nt.setDateTimeAdmission(item.get(4).toString());

                    newToolList.add(nt);
                }


                tableView.setItems(newToolList);

                addButton.setDisable(true);
                editButton.setDisable(true);
                deleteButton.setDisable(true);

            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                showAlert("Ошибка авторизации", "Доступ запрещён: неверный или просроченный токен.");
                addButton.setDisable(true);
            } else if (responseCode >= 400 && responseCode < 500) {
                showAlert("Ошибка клиента", "Ошибка запроса. Код ответа: " + responseCode);
                addButton.setDisable(true);
            } else if (responseCode >= 500) {
                showAlert("Ошибка сервера", "На сервере произошла ошибка. Код ответа: " + responseCode);
                addButton.setDisable(true);
            } else {
                showAlert("Ошибка", "Неожиданный код ответа: " + responseCode);
                addButton.setDisable(true);
            }

        } catch (IOException e) {
            showAlert("Ошибка соединения", "Не удалось соединиться с сервером: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Ошибка", "Произошла ошибка: " + e.getMessage());
        }
    }


    private void loadDataEmployeeToolInfo(String apiUrl) {
        // Проверка, что токен авторизации установлен
        if (token == null || token.isEmpty()) {
            showAlert("Ошибка авторизации", "Токен не установлен. Данные не будут загружены.");
            return;
        }

        try {
            // Создание подключения к серверу по указанному URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Добавление в заголовок запроса токен авторизации
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode(); // Получение кода ответа от сервера

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Если ответ успешный, считывается поток данных
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();

                List<List<Object>> rawList = gson.fromJson(response.toString(), new TypeToken<List<List<Object>>>() {}.getType());

                ObservableList<EmployeeToolInfo> newEmployeeList = FXCollections.observableArrayList();

                for (List<Object> item : rawList) {
                    EmployeeToolInfo emp = new EmployeeToolInfo();
                    emp.setId(item.get(0).toString());
                    emp.setPositionName(item.get(1).toString());
                    emp.setSurname(item.get(2).toString());
                    emp.setName(item.get(3).toString());
                    emp.setPatronymic(item.get(4).toString());
                    emp.setJobTitle(item.get(5).toString());
                    emp.setPhone(item.get(6).toString());
                    emp.setEmail(item.get(7).toString());
                    emp.setAdmissionDate(item.get(8).toString());

                    newEmployeeList.add(emp);
                }

                tableView.setItems(newEmployeeList);

                addButton.setDisable(true);
                editButton.setDisable(true);
                deleteButton.setDisable(true);

            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                showAlert("Ошибка авторизации", "Доступ запрещён: неверный или просроченный токен.");
                addButton.setDisable(true);
            } else if (responseCode >= 400 && responseCode < 500) {
                showAlert("Ошибка клиента", "Ошибка запроса. Код ответа: " + responseCode);
                addButton.setDisable(true);
            } else if (responseCode >= 500) {
                showAlert("Ошибка сервера", "На сервере произошла ошибка. Код ответа: " + responseCode);
                addButton.setDisable(true);
            } else {
                showAlert("Ошибка", "Неожиданный код ответа: " + responseCode);
                addButton.setDisable(true);
            }

        } catch (IOException e) {
            showAlert("Ошибка соединения", "Не удалось соединиться с сервером: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Ошибка", "Произошла ошибка: " + e.getMessage());
        }
    }


    @FXML
    private void handleAddButtonClick() {
        String selectedTable = tableComboBox.getValue();
        // Проверка, что таблица выбрана, иначе показывается ошибка
        if (selectedTable == null || selectedTable.isEmpty()) {
            showAlert("Ошибка", "Сначала выберите таблицу.");
            return;
        }

        // Создание окна
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Добавление записи: " + selectedTable);

        // Создание сетки для размещения элементов формы
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Map<String, TextField> inputFields = new HashMap<>();

        // В зависимости от выбранной таблицы добавляются поля
        switch (selectedTable) {
            case "Места хранения":
                addInputField(grid, inputFields, 0, "name", "Наименование места хранения");
                addInputField(grid, inputFields, 1, "description", "Описание места хранения");
                break;
            case "Инструменты":
                addInputField(grid, inputFields, 0, "articleToolType", "Артикул типа инструмента");
                addInputField(grid, inputFields, 1, "idPlace", "Идентификатор места хранения");
                break;
            case "Типы инструментов":
                addInputField(grid, inputFields, 0, "articleToolType", "Артикул типа инструмента");
                addInputField(grid, inputFields, 1, "name", "Название типа инструмента");
                addInputField(grid, inputFields, 2, "description", "Описание типа инструмента");
                break;
            case "Должности":
                addInputField(grid, inputFields, 0, "titlePosition", "Название должности");
                addInputField(grid, inputFields, 1, "requirements", "Требования к должности");
                addInputField(grid, inputFields, 2, "duties", "Обязанности по должности");
                addInputField(grid, inputFields, 3, "salary", "Зарплата по должности");
                addInputField(grid, inputFields, 4, "role", "Роль (admin, storekeeper, worker)");
                break;
            case "История списаний инструментов":
                addInputField(grid, inputFields, 0, "idTool", "Идентификатор инструмента");
                break;
            case "Сотрудники":
                addInputField(grid, inputFields, 0, "positionId", "Идентификатор должности");
                addInputField(grid, inputFields, 1, "surname", "Фамилия сотрудника");
                addInputField(grid, inputFields, 2, "name", "Имя сотрудника");
                addInputField(grid, inputFields, 3, "patronymic", "Отчество сотрудника");
                addInputField(grid, inputFields, 4, "phoneNumber", "Номер телефона сотрудника");
                addInputField(grid, inputFields, 5, "email", "Email сотрудника");
                addInputField(grid, inputFields, 6, "login", "Логин сотрудника");
                addInputField(grid, inputFields, 7, "password", "Пароль сотрудника");
                break;
            case "История выдачи инструментов":
                addInputField(grid, inputFields, 0, "idEmployee", "Идентификатор сотрудника");
                addInputField(grid, inputFields, 1, "idTool", "Идентификатор инструмента");
                addInputField(grid, inputFields, 2, "action", "Действие (Выдан или Возврат)");
                break;
            default:
                showAlert("Ошибка", "Добавление для выбранной таблицы не реализовано.");
                return;
        }

        Button submitButton = new Button("Добавить");
        Button cancelButton = new Button("Отмена");

        HBox buttons = new HBox(10, submitButton, cancelButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        // Добавление контейнера с кнопками в сетку, под всеми полями ввода
        grid.add(buttons, 0, inputFields.size(), 2, 1);

        submitButton.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение");
            confirm.setHeaderText(null);
            confirm.setContentText("Вы действительно хотите добавить запись?");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Если пользователь подтвердил, собираются данные из всех полей ввода
                    Map<String, String> params = new HashMap<>();
                    for (Map.Entry<String, TextField> entry : inputFields.entrySet()) {
                        params.put(entry.getKey(), entry.getValue().getText());
                    }

                    // Получение URL для отправки POST-запроса для выбранной таблицы
                    String postUrl = getPostUrlForTable(selectedTable);
                    if (postUrl == null) {
                        showAlert("Ошибка", "URL для выбранной таблицы не найден.");
                        return;
                    }

                    sendPostRequest(postUrl, params);

                    refreshTableData(selectedTable);
                }
            });
        });

        cancelButton.setOnAction(e -> modalStage.close());

        Scene scene = new Scene(grid, 450, 50 + inputFields.size() * 40);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    @FXML
    private void handleEditButtonClick() {
        String selectedTable = tableComboBox.getValue();
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();

        // Проверка, выбрана ли таблица
        if (selectedTable == null || selectedTable.isEmpty()) {
            showAlert("Ошибка", "Сначала выберите таблицу.");
            return;
        }
        // Проверка, выбрана ли запись для редактирования
        if (selectedItem == null) {
            showAlert("Ошибка", "Сначала выберите запись для редактирования.");
            return;
        }

        String id;

        // Определение идентификатора записи в зависимости от типа объекта
        if (selectedItem instanceof TypeOfTool) {
            id = ((TypeOfTool) selectedItem).getArticleToolType();
        } else if (selectedItem instanceof StorageLocation) {
            id = ((StorageLocation) selectedItem).getIdPlace();
        } else if (selectedItem instanceof Positions) {
            id = ((Positions) selectedItem).getIdPosition();
        } else if (selectedItem instanceof Employees) {
            id = ((Employees) selectedItem).getIdEmployee();
        } else {
            showAlert("Ошибка", "Выбранный тип записи не поддерживается.");
            return;
        }

        // Проверка, что идентификатор не пустой
        if (id == null || id.isEmpty()) {
            showAlert("Ошибка", "Не удалось определить идентификатор записи.");
            return;
        }

        // Создание окна
        Stage editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.setTitle("Редактирование записи: " + selectedTable);

        // Создание сетки для размещения полей формы
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Словарь для хранения текстовых полей ввода по именам полей
        Map<String, TextField> inputFields = new HashMap<>();
        // Получение текущих значений полей из выбранного объекта
        Map<String, String> currentValues = extractFieldValues(selectedItem);

        // Получение списка полей для редактирования и их метки по выбранной таблице
        List<String[]> fields = getEditFieldsForTable(selectedTable);
        if (fields == null) {
            showAlert("Ошибка", "Редактирование для выбранной таблицы не реализовано.");
            return;
        }

        // Для каждого поля создается метка и текстовое поле, заполняется текущим значением
        for (int i = 0; i < fields.size(); i++) {
            String key = fields.get(i)[0];
            String label = fields.get(i)[1];
            TextField field = new TextField(currentValues.getOrDefault(key, ""));
            grid.add(new Label(label + ":"), 0, i);
            grid.add(field, 1, i);
            inputFields.put(key, field);
        }

        Button saveButton = new Button("Сохранить");
        Button cancelButton = new Button("Отмена");

        HBox buttons = new HBox(10, saveButton, cancelButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttons, 0, fields.size(), 2, 1);

        saveButton.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Подтверждение");
            confirm.setHeaderText(null);
            confirm.setContentText("Вы действительно хотите изменить запись?");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    // Если пользователь подтвердил, собираются обновленные данные из всех полей ввода
                    Map<String, String> updatedParams = new HashMap<>();
                    for (Map.Entry<String, TextField> entry : inputFields.entrySet()) {
                        updatedParams.put(entry.getKey(), entry.getValue().getText());
                    }

                    // Получение URL для PUT-запроса обновления записи
                    String putUrl = getPutUrlForTable(selectedTable, id);
                    if (putUrl == null) {
                        showAlert("Ошибка", "URL для обновления записи не найден.");
                        return;
                    }

                    // Отправка PUT-запрос с обновленными данными на сервер
                    sendPutRequest(putUrl, updatedParams);
                    refreshTableData(selectedTable);
                }
            });


        });

        cancelButton.setOnAction(e -> editStage.close());

        Scene scene = new Scene(grid, 400, 40 + fields.size() * 40);
        editStage.setScene(scene);
        editStage.showAndWait();
    }

    @FXML
    private void handleDeleteButtonClick() {
        String selectedTable = tableComboBox.getValue();
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();

        // Проверка, выбрана ли таблица
        if (selectedTable == null || selectedTable.isEmpty()) {
            showAlert("Ошибка", "Сначала выберите таблицу.");
            return;
        }
        // Проверка, выбрана ли запись для удаления
        if (selectedItem == null) {
            showAlert("Ошибка", "Сначала выберите запись для удаления.");
            return;
        }

        String id;
        String idEmployee;
        String idTool;
        String dateAndTimeIssue;

        // Обработка особого случая для таблицы "История выдачи инструментов"
        if (selectedTable.equals("История выдачи инструментов")) {
            id = "";
            if (selectedItem instanceof HistoryToolIssue) {
                // Получение составных ключей для удаления записи
                idEmployee = ((HistoryToolIssue) selectedItem).getIdEmployee();
                idTool = ((HistoryToolIssue) selectedItem).getIdTool();
                dateAndTimeIssue = ((HistoryToolIssue) selectedItem).getDateTimeIssue();
            } else {
                dateAndTimeIssue = "";
                idTool = "";
                idEmployee = "";
                showAlert("Ошибка", "Выбранный тип записи не поддерживается для удаления.");
                return;
            }
            // Проверка, что ключи определены
            if (idEmployee == null || idTool == null || dateAndTimeIssue == null) {
                showAlert("Ошибка", "Не удалось определить ключи для удаления записи.");
                return;
            }
        } else {
            dateAndTimeIssue = "";
            idTool = "";
            idEmployee = "";
            // Определение идентификатора записи в зависимости от типа объекта
            if (selectedItem instanceof TypeOfTool) {
                id = ((TypeOfTool) selectedItem).getArticleToolType();
            } else if (selectedItem instanceof StorageLocation) {
                id = ((StorageLocation) selectedItem).getIdPlace();
            } else if (selectedItem instanceof Positions) {
                id = ((Positions) selectedItem).getIdPosition();
            } else if (selectedItem instanceof Employees) {
                id = ((Employees) selectedItem).getIdEmployee();
            } else if (selectedItem instanceof Tools) {
                id = ((Tools) selectedItem).getIdTool();
            }
            else {
                // Если тип объекта не поддерживается — показываается ошибка
                id = "";
                showAlert("Ошибка", "Выбранный тип записи не поддерживается для удаления.");
                return;
            }
            // Проверка, что идентификатор не пустой
            if (id == null || id.isEmpty()) {
                showAlert("Ошибка", "Не удалось определить идентификатор записи.");
                return;
            }
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Подтверждение");
        confirm.setHeaderText(null);
        confirm.setContentText("Вы действительно хотите удалить запись?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Если пользователь подтвердил, отправка запроса на удаление
                if (selectedTable.equals("История выдачи инструментов")) {
                    // Для "Истории выдачи инструментов" удаление с телом запроса (несколько параметров)
                    String deleteUrl = getDeleteUrlForTable(selectedTable);
                    Map<String, String> params = new HashMap<>();
                    params.put("idEmployee", idEmployee);
                    params.put("idTool", idTool);
                    params.put("dateAndTimeIssue", dateAndTimeIssue);

                    sendDeleteRequestWithBody(deleteUrl, params);
                } else {
                    // Для остальных таблиц удаление по URL с идентификатором
                    String deleteUrl = getDeleteUrlForTable(selectedTable) + id;
                    sendDeleteRequest(deleteUrl);
                }
                refreshTableData(selectedTable);
            }
        });
    }


    private void sendDeleteRequestWithBody(String urlString, Map<String, String> params) {
        try {
            // Создание подключения к серверу по указанному URL
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            // Добавление заголовка авторизации с токеном
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setDoOutput(true);

            // Формирование тела запроса в формате URL-кодированных параметров
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            // Отправка сформированных данных в тело запроса
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode(); // Получение кода ответа сервера

            if (responseCode >= 200 && responseCode < 300) {
                showAlert("Успех", "Запись успешно удалена.");
            } else {
                StringBuilder response = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                connection.getErrorStream() != null ? connection.getErrorStream() : connection.getInputStream(),
                                StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line.trim());
                    }
                }
                showAlert("Ошибка", "Ошибка при удалении: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Произошла ошибка при отправке запроса на удаление: " + e.getMessage());
        }
    }


    private void sendPostRequest(String urlString, Map<String, String> params) {
        try {
            // Создание подключения к серверу по указанному URL
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // Добавление заголовка авторизации с токеном
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setDoOutput(true);

            // Формирование тела запроса в формате URL-кодированных параметров
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

            // Отправка сформированных данных в тело запроса
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postDataBytes);
            }

            int responseCode = connection.getResponseCode(); // Получение кода ответа сервера

            BufferedReader br;
            // В зависимости от кода ответа выбирается поток для чтения
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String responseLine;
            // Считывание ответа сервера
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (responseCode >= 200 && responseCode < 300) {
                showAlert("Успех", "Операция успешно выполнена:\n" + response.toString());
            } else {
                showAlert("Ошибка", "Ошибка от сервера (код " + responseCode + "):\n" + response.toString());
            }

        } catch (Exception e) {
            showAlert("Ошибка", "Ошибка при отправке запроса: некорректные данные (тип данных) или передано пустое значение.\n" + e.getMessage());
        }
    }

    private void sendPutRequest(String urlString, Map<String, String> params) {
        try {
            // Создание подключения к серверу по указанному URL
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            // Добавление заголовка авторизации с токеном
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setDoOutput(true);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            // Отправка сформированных данных в тело запроса
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode(); // Получение кода ответа сервера

            BufferedReader br;
            // В зависимости от кода ответа выбирается поток для чтения
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String responseLine;
            // Считывание ответа сервера
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (responseCode >= 200 && responseCode < 300) {
                showAlert("Успех", "Операция успешно выполнена:\n" + response.toString());
            } else {
                showAlert("Ошибка", "Ошибка от сервера (код " + responseCode + "):\n" + response.toString());
            }

        } catch (Exception e) {
            showAlert("Ошибка", "Произошла ошибка при отправке запроса, возможно введены некорректные или не существующие данные");
        }
    }

    private void sendDeleteRequest(String urlString) {
        try {
            // Создание подключения к серверу по указанному URL
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            // Добавление заголовка авторизации с токеном
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode(); // Получение кода ответа сервера

            BufferedReader br;
            // В зависимости от кода ответа выбирается поток для чтения
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String responseLine;
            // Считывание ответа сервера
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (responseCode >= 200 && responseCode < 300) {
                showAlert("Успех", "Операция успешно выполнена:\n" + response.toString());
            } else {
                showAlert("Ошибка", "Ошибка от сервера (код " + responseCode + "):\n" + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Произошла ошибка при отправке запроса на удаление: " + e.getMessage());
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 0);

        alert.getDialogPane().setContent(content);

        alert.getDialogPane().setMinSize(400, 200);
        alert.getDialogPane().setPrefSize(600, 300);

        alert.showAndWait();
    }




    private void clearTable() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }

    private <T> void setupTableColumns(Class<T> currentClass) {
        tableView.getColumns().clear();

        //Настройка колонок в зависимости от типа данных (класса), которые будут отображаться
        if (currentClass == Tools.class) {
            setupToolColumns();
        }
        else if (currentClass == StorageLocation.class) {
            setupStorageLocationColumns();
        } else if (currentClass == TypeOfTool.class) {
            setupTypeOfToolColumns();
        } else if (currentClass == Positions.class) {
            setupPositionsColumns();
        } else if (currentClass == HistoryWriteOff.class) {
            setupHistoryWriteOffColumns();
        } else if (currentClass == Employees.class) {
            setupEmployeesColumns();
        } else if (currentClass == HistoryToolIssue.class) {
            setupHistoryToolIssueColumns();
        } else if (currentClass == ToolInfo.class) {
            setupToolInfo();
        } else if (currentClass == EmployeeToolInfo.class) {
            setupEmployeeToolInfo();
        }
    }

    private void addInputField(GridPane grid, Map<String, TextField> inputs, int row, String key, String labelText) {
        // Добавление пары метка + текстовое поле в заданную строку сетки
        Label label = new Label(labelText + ":");
        TextField textField = new TextField();
        grid.add(label, 0, row);
        grid.add(textField, 1, row);
        inputs.put(key, textField);
    }

    private Map<String, String> extractFieldValues(Object obj) {
        // Извлечение значений всех свойств объекта через геттеры и возвращение их в виде пары ключ-значение
        Map<String, String> map = new HashMap<>();
        try {
            for (Method method : obj.getClass().getMethods()) {
                if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                    String key = method.getName().substring(3);
                    key = Character.toLowerCase(key.charAt(0)) + key.substring(1);
                    if (key.equals("idPosition")) {
                        key = "positionId";
                    }
                    Object value = method.invoke(obj);
                    map.put(key, value != null ? value.toString() : "");
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    private List<String[]> getEditFieldsForTable(String tableName) {
        // Возвращение списка полей для редактирования, которые соответствуют выбранной таблице
        switch (tableName) {
            case "Типы инструментов":
                return Arrays.asList(
                        new String[]{"name", "Название (Опционально)"},
                        new String[]{"description", "Описание (Опционально)"}
                );
            case "Места хранения":
                return Arrays.asList(
                        new String[]{"name", "Наименование (Опционально)"},
                        new String[]{"description", "Описание (Опционально)"}
                );
            case "Должности":
                return Arrays.asList(
                        new String[]{"titlePosition", "Название (Опционально)"},
                        new String[]{"requirements", "Требования (Опционально)"},
                        new String[]{"duties", "Обязанности (Опционально)"},
                        new String[]{"salary", "Зарплата (Опционально)"},
                        new String[]{"role", "Роль (Опционально)"}
                );
            case "Сотрудники":
                return Arrays.asList(
                        new String[]{"positionId", "ID должности (Опционально)"},
                        new String[]{"surname", "Фамилия (Опционально)"},
                        new String[]{"name", "Имя (Опционально)"},
                        new String[]{"patronymic", "Отчество (Опционально)"},
                        new String[]{"phoneNumber", "Телефон (Опционально)"},
                        new String[]{"email", "Email (Опционально)"},
                        new String[]{"login", "Логин (Опционально)"},
                        new String[]{"password", "Пароль (Опционально)"}
                );
            default:
                return null;
        }
    }

    private String getPostUrlForTable(String tableName) {
        // Возвращение URL для добавления записи, в зависимости от выбранной таблицы
        switch (tableName) {
            case "Места хранения": return "http://localhost:8080/storage-locations";
            case "Инструменты": return "http://localhost:8080/tools";
            case "Типы инструментов": return "http://localhost:8080/types-of-tools";
            case "Должности": return "http://localhost:8080/positions";
            case "История списаний инструментов": return "http://localhost:8080/history-write-off-instruments";
            case "Сотрудники": return "http://localhost:8080/employees";
            case "История выдачи инструментов": return "http://localhost:8080/history-tool-issues";
            default: return null;
        }
    }

    private String getPutUrlForTable(String tableName, String id) {
        // Возвращение URL для изменения записи, в зависимости от выбранной таблицы
        switch (tableName) {
            case "Типы инструментов": return "http://localhost:8080/types-of-tools/" + id;
            case "Места хранения": return "http://localhost:8080/storage-locations/" + id;
            case "Должности": return "http://localhost:8080/positions/" + id;
            case "Сотрудники": return "http://localhost:8080/employees/" + id;
            default: return null;
        }
    }

    private String getDeleteUrlForTable(String tableName) {
        // Возвращение URL для удаления записи, в зависимости от выбранной таблицы
        switch (tableName) {
            case "Типы инструментов":
                return "http://localhost:8080/types-of-tools/";
            case "Инструменты":
                return "http://localhost:8080/tools/";
            case "Места хранения":
                return "http://localhost:8080/storage-locations/";
            case "Должности":
                return "http://localhost:8080/positions/";
            case "Сотрудники":
                return "http://localhost:8080/employees/";
            case "История выдачи инструментов":
                return "http://localhost:8080/history-tool-issues";
            default:
                return null;
        }
    }

    private void refreshTableData(String tableName) {
        // Обновление выбранной таблицы
        switch (tableName) {
            case "Места хранения": loadStorageLocationData(); break;
            case "Инструменты": loadToolsData(); break;
            case "Типы инструментов": loadTypeOfToolData(); break;
            case "Должности": loadPositionsData(); break;
            case "История списаний инструментов": loadHistoryWriteOffData(); break;
            case "Сотрудники": loadEmployeesData(); break;
            case "История выдачи инструментов": loadHistoryToolIssue(); break;
            case "Информация об инструментах": loadToolInfo(); break;
            case "Информация об инструментах у рабочих": loadEmployeeToolInfo(); break;
        }
    }

    public void openAuthorizationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("authorizationWindow.fxml"));
            Scene authorizationScene = new Scene(loader.load(), 640, 520);
            Stage authorizationStage = new Stage();
            authorizationStage.setTitle("Вход в систему");
            authorizationStage.setScene(authorizationScene);
            authorizationStage.centerOnScreen();
            authorizationStage.show();
            ((Stage) addButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openProcedures() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("proceduresWindow.fxml"));
            Scene proceduresScene = new Scene((Parent)loader.load(), (double)1100.0F, (double)600.0F);
            Stage authorizationStage = new Stage();
            authorizationStage.setTitle("Процедуры");
            authorizationStage.setScene(proceduresScene);
            authorizationStage.centerOnScreen();
            authorizationStage.show();
            ProceduresWindowController proceduresWindowController = (ProceduresWindowController)loader.getController();
            proceduresWindowController.setToken(this.token);
            ((Stage)this.proceduresButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
