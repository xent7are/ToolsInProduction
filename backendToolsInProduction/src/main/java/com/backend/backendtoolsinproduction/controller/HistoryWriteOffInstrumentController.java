package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.HistoryWriteOffInstrument;
import com.backend.backendtoolsinproduction.service.HistoryWriteOffInstrumentService;
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

// Контроллер для управления историей списания инструментов, предоставляет CRUD-операции
@RestController
@RequestMapping("/history-write-off-instruments")
@SecurityRequirement(name = "bearerAuth")
public class HistoryWriteOffInstrumentController {

    @Autowired
    // Сервис для работы с историей списания инструментов
    private HistoryWriteOffInstrumentService historyWriteOffInstrumentService;

    // Получение списка всех записей истории списания с обработкой ошибок
    @Operation(
            summary = "Получение списка всех записей истории списания",
            description = "Возвращает список всех записей истории списания. Доступно для ролей ADMIN и STOREKEEPER."
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
    public ResponseEntity<?> getAllHistoryWriteOffInstruments() {
        try {
            // Получение списка всех записей истории списания через сервис
            List<HistoryWriteOffInstrument> historyWriteOffInstruments = historyWriteOffInstrumentService.getAllHistoryWriteOffInstruments();
            if (historyWriteOffInstruments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Записи истории списания не найдены.");
            }
            return ResponseEntity.ok(historyWriteOffInstruments);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записей
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка записей истории списания: " + e.getMessage());
        }
    }

    // Получение списка записей истории списания с пагинацией
    @Operation(
            summary = "Получение списка записей истории списания с пагинацией",
            description = "Возвращает список записей истории списания с пагинацией. Доступно для ролей ADMIN и STOREKEEPER."
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
    public ResponseEntity<?> getAllHistoryWriteOffInstrumentsWithPagination(
            @Parameter(description = "Номер страницы", required = false)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество записей на странице", required = false)
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Получение списка записей истории списания с пагинацией через сервис
            Page<HistoryWriteOffInstrument> historyWriteOffInstrumentsPage = historyWriteOffInstrumentService.getAllHistoryWriteOffInstrumentsWithPagination(page, size);
            if (historyWriteOffInstrumentsPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Записи истории списания не найдены.");
            }
            return ResponseEntity.ok(historyWriteOffInstrumentsPage);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записей
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка записей с пагинацией: " + e.getMessage());
        }
    }

    // Получение записи истории списания по идентификатору с обработкой ошибок
    @Operation(
            summary = "Получение записи истории списания по ID",
            description = "Возвращает запись истории списания по ID. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Запись успешно получена."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Запись не найдена."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getHistoryWriteOffInstrumentById(
            @Parameter(description = "Идентификатор записи списания", required = true)
            @PathVariable String id) {
        try {
            // Поиск записи истории списания по идентификатору через сервис
            HistoryWriteOffInstrument historyWriteOffInstrument = historyWriteOffInstrumentService.getHistoryWriteOffInstrumentById(id);
            return ResponseEntity.ok(historyWriteOffInstrument);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия записи
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении записи истории списания: " + e.getMessage());
        }
    }

    // Создание новой записи списания инструмента с обработкой ошибок
    @Operation(
            summary = "Создание новой записи списания (списание инструмента)",
            description = "Создает запись списания, удаляя инструмент. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Инструмент успешно списан."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструмент не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> writeOffTool(
            @Parameter(description = "Идентификатор инструмента", required = true)
            @RequestParam String idTool) {
        try {
            // Списание инструмента через сервис
            historyWriteOffInstrumentService.writeOffTool(idTool);
            return ResponseEntity.status(HttpStatus.CREATED).body("Инструмент успешно списан.");
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при списании инструмента: " + e.getMessage());
        }
    }
}