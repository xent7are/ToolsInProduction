package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.Position;
import com.backend.backendtoolsinproduction.service.PositionService;
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

// Контроллер для управления должностями, предоставляет CRUD-операции
@RestController
@RequestMapping("/positions")
@SecurityRequirement(name = "bearerAuth")
public class PositionController {

    @Autowired
    // Сервис для работы с должностями
    private PositionService positionService;

    // Получение списка всех должностей с обработкой ошибок
    @Operation(
            summary = "Получение списка всех должностей",
            description = "Возвращает список всех должностей. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список должностей успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Должности не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllPositions() {
        try {
            // Получение списка всех должностей через сервис
            List<Position> positions = positionService.getAllPositions();
            if (positions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Должности не найдены.");
            }
            return ResponseEntity.ok(positions);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия должностей
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка должностей: " + e.getMessage());
        }
    }

    // Получение должности по идентификатору с обработкой ошибок
    @Operation(
            summary = "Получение должности по ID",
            description = "Возвращает должность по ID. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Должность успешно получена."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Должность не найдена."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getPositionById(
            @Parameter(description = "Идентификатор должности", required = true)
            @PathVariable String id) {
        try {
            // Поиск должности по идентификатору через сервис
            Position position = positionService.getPositionById(id);
            return ResponseEntity.ok(position);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия должности
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении должности: " + e.getMessage());
        }
    }

    // Получение должности по названию с обработкой ошибок
    @Operation(
            summary = "Получение должности по названию",
            description = "Возвращает должность по названию. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Должность успешно получена."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Должность не найдена."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/title/{titlePosition}")
    public ResponseEntity<?> getPositionByTitle(
            @Parameter(description = "Название должности", required = true)
            @PathVariable String titlePosition) {
        try {
            // Поиск должности по названию через сервис
            Position position = positionService.getPositionByTitle(titlePosition);
            return ResponseEntity.ok(position);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия должности
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении должности: " + e.getMessage());
        }
    }

    // Создание новой должности с обработкой ошибок
    @Operation(
            summary = "Создание новой должности",
            description = "Создает новую должность. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Должность успешно создана."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры или название должности уже существует."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> createPosition(
            @Parameter(description = "Название должности", required = true)
            @RequestParam String titlePosition,
            @Parameter(description = "Требования к должности", required = true)
            @RequestParam String requirements,
            @Parameter(description = "Обязанности по должности", required = true)
            @RequestParam String duties,
            @Parameter(description = "Зарплата по должности", required = true)
            @RequestParam double salary,
            @Parameter(description = "Роль (admin, storekeeper, worker)", required = true)
            @RequestParam String role) {
        try {
            // Создание новой должности через сервис
            Position newPosition = positionService.createPosition(titlePosition, requirements, duties, salary, role);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPosition);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при создании должности: " + e.getMessage());
        }
    }

    // Обновление должности с обработкой ошибок
    @Operation(
            summary = "Обновление должности по ID",
            description = "Обновляет должность по ID. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Должность успешно обновлена."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры или название должности уже существует."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Должность не найдена."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePosition(
            @Parameter(description = "Идентификатор должности", required = true)
            @PathVariable String id,
            @Parameter(description = "Новое название должности (опционально)", required = false)
            @RequestParam(required = false) String titlePosition,
            @Parameter(description = "Новые требования (опционально)", required = false)
            @RequestParam(required = false) String requirements,
            @Parameter(description = "Новые обязанности (опционально)", required = false)
            @RequestParam(required = false) String duties,
            @Parameter(description = "Новая зарплата (опционально)", required = false)
            @RequestParam(required = false) Double salary,
            @Parameter(description = "Новая роль (опционально)", required = false)
            @RequestParam(required = false) String role) {
        try {
            // Обновление должности через сервис
            Position updatedPosition = positionService.updatePosition(id, titlePosition, requirements, duties, salary, role);
            return ResponseEntity.ok(updatedPosition);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия должности
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при обновлении должности: " + e.getMessage());
        }
    }

    // Удаление должности с обработкой ошибок
    @Operation(
            summary = "Удаление должности по ID",
            description = "Удаляет должность по ID. Доступно только для роли ADMIN."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Должность успешно удалена."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Должность не найдена."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosition(
            @Parameter(description = "Идентификатор должности", required = true)
            @PathVariable String id) {
        try {
            // Удаление должности через сервис
            positionService.deletePosition(id);
            return ResponseEntity.ok("Должность успешно удалена.");
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия должности
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при удалении должности: " + e.getMessage());
        }
    }
}