package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.StorageLocation;
import com.backend.backendtoolsinproduction.service.StorageLocationService;
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

// Контроллер для управления местами хранения, предоставляет CRUD-операции
@RestController
@RequestMapping("/storage-locations")
@SecurityRequirement(name = "bearerAuth")
public class StorageLocationController {

    @Autowired
    // Сервис для работы с местами хранения
    private StorageLocationService storageLocationService;

    // Получение списка всех мест хранения с обработкой ошибок
    @Operation(
            summary = "Получение списка всех мест хранения",
            description = "Возвращает список всех мест хранения. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список мест хранения успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Места хранения не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllStorageLocations() {
        try {
            // Получение списка всех мест хранения через сервис
            List<StorageLocation> storageLocations = storageLocationService.getAllStorageLocations();
            if (storageLocations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Места хранения не найдены.");
            }
            return ResponseEntity.ok(storageLocations);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия мест хранения
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка мест хранения: " + e.getMessage());
        }
    }

    // Получение места хранения по идентификатору с обработкой ошибок
    @Operation(
            summary = "Получение места хранения по ID",
            description = "Возвращает место хранения по ID. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Место хранения успешно получено."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Место хранения не найдено."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getStorageLocationById(
            @Parameter(description = "Идентификатор места хранения", required = true)
            @PathVariable String id) {
        try {
            // Поиск места хранения по идентификатору через сервис
            StorageLocation storageLocation = storageLocationService.getStorageLocationById(id);
            return ResponseEntity.ok(storageLocation);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия места хранения
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении места хранения: " + e.getMessage());
        }
    }

    // Получение места хранения по названию с обработкой ошибок
    @Operation(
            summary = "Получение места хранения по названию",
            description = "Возвращает место хранения по названию. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Место хранения успешно получено."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Место хранения не найдено."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getStorageLocationByName(
            @Parameter(description = "Название места хранения", required = true)
            @PathVariable String name) {
        try {
            // Поиск места хранения по названию через сервис
            StorageLocation storageLocation = storageLocationService.getStorageLocationByName(name);
            return ResponseEntity.ok(storageLocation);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия места хранения
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении места хранения: " + e.getMessage());
        }
    }

    // Создание нового места хранения с обработкой ошибок
    @Operation(
            summary = "Создание нового места хранения",
            description = "Создает новое место хранения. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Место хранения успешно создано."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> createStorageLocation(
            @Parameter(description = "Название места хранения", required = true)
            @RequestParam String name,
            @Parameter(description = "Описание места хранения", required = true)
            @RequestParam String description) {
        try {
            // Создание нового места хранения через сервис
            StorageLocation newStorageLocation = storageLocationService.createStorageLocation(name, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStorageLocation);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при создании места хранения: " + e.getMessage());
        }
    }
}