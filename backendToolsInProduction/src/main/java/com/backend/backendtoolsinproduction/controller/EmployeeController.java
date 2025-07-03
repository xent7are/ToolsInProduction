package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.Employee;
import com.backend.backendtoolsinproduction.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

// Контроллер для управления сотрудниками, предоставляет CRUD-операции и поиск
@RestController
@RequestMapping("/employees")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    @Autowired
    // Сервис для работы с сотрудниками
    private EmployeeService employeeService;

    // Получение списка всех сотрудников с обработкой ошибок
    @Operation(
            summary = "Получение списка всех сотрудников",
            description = "Возвращает список всех сотрудников. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список сотрудников успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Сотрудники не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllEmployees() {
        try {
            // Получение списка всех сотрудников через сервис
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Сотрудники не найдены.");
            }
            return ResponseEntity.ok(employees);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия сотрудников
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка сотрудников: " + e.getMessage());
        }
    }

    // Получение сотрудника по идентификатору с обработкой ошибок
    @Operation(
            summary = "Получение сотрудника по ID",
            description = "Возвращает сотрудника по ID. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Сотрудник успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Сотрудник не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(
            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathVariable String id) {
        try {
            // Поиск сотрудника по идентификатору через сервис
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия сотрудника
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении сотрудника: " + e.getMessage());
        }
    }

    // Получение сотрудника по email с обработкой ошибок
    @Operation(
            summary = "Получение сотрудника по email",
            description = "Возвращает сотрудника по email. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Сотрудник успешно получен."),
                    @ApiResponse(responseCode = "400", description = "Некорректный формат email."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Сотрудник не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getEmployeeByEmail(
            @Parameter(description = "Email сотрудника", required = true)
            @PathVariable String email) {
        try {
            // Поиск сотрудника по email через сервис
            Employee employee = employeeService.getEmployeeByEmail(email);
            return ResponseEntity.ok(employee);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректного формата email
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия сотрудника
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении сотрудника: " + e.getMessage());
        }
    }

    // Получение списка сотрудников по должности с обработкой ошибок
    @Operation(
            summary = "Получение сотрудников по должности",
            description = "Возвращает список сотрудников по должности. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список сотрудников успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Сотрудники не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/position/{titlePosition}")
    public ResponseEntity<?> getEmployeesByPosition(
            @Parameter(description = "Название должности", required = true)
            @PathVariable String titlePosition) {
        try {
            // Получение списка сотрудников по должности через сервис
            List<Employee> employees = employeeService.getEmployeesByPosition(titlePosition);
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Сотрудники с должностью " + titlePosition + " не найдены.");
            }
            return ResponseEntity.ok(employees);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия сотрудников или должности
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка сотрудников: " + e.getMessage());
        }
    }

    // Создание нового сотрудника с обработкой ошибок
    @Operation(
            summary = "Создание нового сотрудника",
            description = "Создает нового сотрудника. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Сотрудник успешно создан."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Должность не найдена."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> createEmployee(
            @Parameter(description = "Идентификатор должности", required = true)
            @RequestParam String positionId,
            @Parameter(description = "Фамилия сотрудника", required = true)
            @RequestParam String surname,
            @Parameter(description = "Имя сотрудника", required = true)
            @RequestParam String name,
            @Parameter(description = "Отчество сотрудника", required = true)
            @RequestParam String patronymic,
            @Parameter(description = "Номер телефона сотрудника", required = true)
            @RequestParam String phoneNumber,
            @Parameter(description = "Email сотрудника", required = true)
            @RequestParam String email,
            @Parameter(description = "Логин сотрудника", required = true)
            @RequestParam String login,
            @Parameter(description = "Пароль сотрудника", required = true)
            @RequestParam String password) {
        try {
            // Создание нового сотрудника через сервис
            Employee newEmployee = employeeService.createEmployee(positionId, surname, name, patronymic, phoneNumber, email, login, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия должности
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при создании сотрудника: " + e.getMessage());
        }
    }

    // Обновление сотрудника по идентификатору с обработкой ошибок
    @Operation(
            summary = "Обновление сотрудника по ID",
            description = "Обновляет сотрудника по ID. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Сотрудник успешно обновлен."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Сотрудник или должность не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathVariable String id,
            @Parameter(description = "Новый идентификатор должности (опционально)", required = false)
            @RequestParam(required = false) String positionId,
            @Parameter(description = "Новая фамилия (опционально)", required = false)
            @RequestParam(required = false) String surname,
            @Parameter(description = "Новое имя (опционально)", required = false)
            @RequestParam(required = false) String name,
            @Parameter(description = "Новое отчество (опционально)", required = false)
            @RequestParam(required = false) String patronymic,
            @Parameter(description = "Новый номер телефона (опционально)", required = false)
            @RequestParam(required = false) String phoneNumber,
            @Parameter(description = "Новый email (опционально)", required = false)
            @RequestParam(required = false) String email,
            @Parameter(description = "Новый логин (опционально)", required = false)
            @RequestParam(required = false) String login,
            @Parameter(description = "Новый пароль (оп манипуляция)", required = false)
            @RequestParam(required = false) String password) {
        try {
            // Обновление сотрудника через сервис
            Employee updatedEmployee = employeeService.updateEmployee(id, positionId, surname, name, patronymic, phoneNumber, email, login, password);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия сотрудника или должности
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при обновлении сотрудника: " + e.getMessage());
        }
    }

    // Удаление сотрудника по идентификатору с обработкой ошибок
    @Operation(
            summary = "Удаление сотрудника по ID",
            description = "Удаляет сотрудника по ID. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Сотрудник успешно удален."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Сотрудник не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(
            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathVariable String id) {
        try {
            // Удаление сотрудника по идентификатору через сервис
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Сотрудник успешно удален.");
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия сотрудника
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при удалении сотрудника: " + e.getMessage());
        }
    }
}