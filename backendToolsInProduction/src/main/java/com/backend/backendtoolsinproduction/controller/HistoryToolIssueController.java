package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.HistoryToolIssue;
import com.backend.backendtoolsinproduction.service.HistoryToolIssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

// Контроллер для управления историей выдачи инструментов, предоставляет CRUD-операции, вызов процедур и представлений
@RestController
@RequestMapping("/history-tool-issues")
@SecurityRequirement(name = "bearerAuth")
public class HistoryToolIssueController {

    @Autowired
    // Сервис для работы с историей выдачи инструментов
    private HistoryToolIssueService historyToolIssueService;

    // Получение списка всех записей истории выдачи с обработкой ошибок
    @Operation(
            summary = "Получение списка всех записей истории выдачи",
            description = "Возвращает список всех записей истории выдачи инструментов. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список записей успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Записи не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllHistoryToolIssues() {
        try {
            // Получение списка всех записей истории выдачи через сервис
            List<HistoryToolIssue> historyToolIssues = historyToolIssueService.getAllHistoryToolIssues();
            if (historyToolIssues.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Записи истории выдачи не найдены.");
            }
            return ResponseEntity.ok(historyToolIssues);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записей
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка записей истории выдачи: " + e.getMessage());
        }
    }

    // Получение списка записей истории выдачи с пагинацией
    @Operation(
            summary = "Получение списка записей истории выдачи с пагинацией",
            description = "Возвращает список записей истории выдачи с пагинацией. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список записей успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Записи не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/paginated")
    public ResponseEntity<?> getAllHistoryToolIssuesWithPagination(
            @Parameter(description = "Номер страницы", required = false)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество записей на странице", required = false)
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Получение списка записей истории выдачи с пагинацией через сервис
            Page<HistoryToolIssue> historyToolIssuesPage = historyToolIssueService.getAllHistoryToolIssuesWithPagination(page, size);
            if (historyToolIssuesPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Записи истории выдачи не найдены.");
            }
            return ResponseEntity.ok(historyToolIssuesPage);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записей
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка записей с пагинацией: " + e.getMessage());
        }
    }

    // Получение записей истории выдачи по сотруднику с обработкой ошибок
    @Operation(
            summary = "Получение записей истории выдачи по сотруднику",
            description = "Возвращает записи истории выдачи для сотрудника. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список записей успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Записи или сотрудник не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/employee/{idEmployee}")
    public ResponseEntity<?> getHistoryToolIssuesByEmployee(
            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathVariable String idEmployee) {
        try {
            // Получение списка записей истории выдачи для сотрудника через сервис
            List<HistoryToolIssue> historyToolIssues = historyToolIssueService.getHistoryToolIssuesByEmployee(idEmployee);
            if (historyToolIssues.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Записи истории выдачи для сотрудника с ID " + idEmployee + " не найдены.");
            }
            return ResponseEntity.ok(historyToolIssues);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записей или сотрудника
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении записей истории выдачи: " + e.getMessage());
        }
    }

    // Получение записей истории выдачи по инструменту с обработкой ошибок
    @Operation(
            summary = "Получение записей истории выдачи по инструменту",
            description = "Возвращает записи истории выдачи для инструмента. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список записей успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Записи или инструмент не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/tool/{idTool}")
    public ResponseEntity<?> getHistoryToolIssuesByTool(
            @Parameter(description = "Идентификатор инструмента", required = true)
            @PathVariable String idTool) {
        try {
            // Получение списка записей истории выдачи для инструмента через сервис
            List<HistoryToolIssue> historyToolIssues = historyToolIssueService.getHistoryToolIssuesByTool(idTool);
            if (historyToolIssues.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Записи истории выдачи для инструмента с ID " + idTool + " не найдены.");
            }
            return ResponseEntity.ok(historyToolIssues);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записей или инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении записей истории выдачи: " + e.getMessage());
        }
    }

    // Получение информации об инструментах в использовании с обработкой ошибок
    @Operation(
            summary = "Получение информации об инструментах в использовании",
            description = "Возвращает данные из представления tools_in_use. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Информация успешно получена."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/tools-in-use")
    public ResponseEntity<?> getToolsInUse() {
        try {
            // Получение данных об инструментах в использовании через сервис
            List<Object[]> toolsInUse = historyToolIssueService.getToolsInUse();
            if (toolsInUse.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Инструменты в использовании не найдены.");
            }
            return ResponseEntity.ok(toolsInUse);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия данных
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении информации об инструментах в использовании: " + e.getMessage());
        }
    }

    // Получение инструментов, используемых сотрудником, с обработкой ошибок
    @Operation(
            summary = "Получение инструментов, используемых сотрудником",
            description = "Возвращает инструменты сотрудника через процедуру. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список инструментов успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/tools-in-use-by-employee/{idEmployee}")
    public ResponseEntity<?> getToolsInUseByEmployee(
            @Parameter(description = "Идентификатор сотрудника", required = true)
            @PathVariable String idEmployee) {
        try {
            // Получение списка инструментов, используемых сотрудником, через сервис
            List<Object[]> toolsInUseByEmployee = historyToolIssueService.getToolsInUseByEmployee(idEmployee);
            if (toolsInUseByEmployee.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Инструменты, используемые сотрудником с ID " + idEmployee + ", не найдены.");
            }
            return ResponseEntity.ok(toolsInUseByEmployee);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструментов или сотрудника
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка инструментов: " + e.getMessage());
        }
    }

    // Создание новой записи истории выдачи с обработкой ошибок
    @Operation(
            summary = "Создание новой записи истории выдачи",
            description = "Создает новую запись выдачи инструмента. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Запись успешно создана."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Сотрудник или инструмент не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> createHistoryToolIssue(
            @Parameter(description = "Идентификатор сотрудника", required = true)
            @RequestParam String idEmployee,
            @Parameter(description = "Идентификатор инструмента", required = true)
            @RequestParam String idTool,
            @Parameter(description = "Действие (Выдан или Возврат)", required = true)
            @RequestParam String action) {
        try {
            // Создание новой записи истории выдачи через сервис
            HistoryToolIssue newHistoryToolIssue = historyToolIssueService.createHistoryToolIssue(idEmployee, idTool, action);
            return ResponseEntity.status(HttpStatus.CREATED).body(newHistoryToolIssue);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Обработка ошибки некорректных параметров или состояния
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия сотрудника или инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при создании записи истории выдачи: " + e.getMessage());
        }
    }

    // Удаление записи истории выдачи с обработкой ошибок
    @Operation(
            summary = "Удаление записи истории выдачи",
            description = "Удаляет запись истории выдачи. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Запись успешно удалена."),
                    @ApiResponse(responseCode = "400", description = "Некорректный формат даты."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Запись не найдена."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @DeleteMapping("")
    public ResponseEntity<?> deleteHistoryToolIssue(
            @Parameter(description = "Идентификатор сотрудника", required = true)
            @RequestParam String idEmployee,
            @Parameter(description = "Идентификатор инструмента", required = true)
            @RequestParam String idTool,
            @Parameter(description = "Дата и время выдачи", required = true)
            @RequestParam String dateAndTimeIssue) {
        try {
            // Удаление записи истории выдачи через сервис
            historyToolIssueService.deleteHistoryToolIssue(idEmployee, idTool, dateAndTimeIssue);
            return ResponseEntity.ok("Запись успешно удалена.");
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректного формата даты
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записи
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при удалении записи истории выдачи: " + e.getMessage());
        }
    }
}