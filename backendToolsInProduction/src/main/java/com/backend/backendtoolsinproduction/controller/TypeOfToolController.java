package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.TypeOfTool;
import com.backend.backendtoolsinproduction.service.TypeOfToolService;
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

// Контроллер для управления типами инструментов, предоставляет CRUD-операции
@RestController
@RequestMapping("/types-of-tools")
@SecurityRequirement(name = "bearerAuth")
public class TypeOfToolController {

    @Autowired
    // Сервис для работы с типами инструментов
    private TypeOfToolService typeOfToolService;

    // Получение списка всех типов инструментов с обработкой ошибок
    @Operation(
            summary = "Получение списка всех типов инструментов",
            description = "Возвращает список всех типов инструментов. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список типов успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Типы не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllTypesOfTools() {
        try {
            // Получение списка всех типов инструментов через сервис
            List<TypeOfTool> typesOfTools = typeOfToolService.getAllTypesOfTools();
            // Проверка на наличие данных
            if (typesOfTools.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Типы инструментов не найдены.");
            }
            return ResponseEntity.ok(typesOfTools);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия типов инструментов
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка типов инструментов: " + e.getMessage());
        }
    }

    // Получение типа инструмента по идентификатору с обработкой ошибок
    @Operation(
            summary = "Получение типа инструмента по ID",
            description = "Возвращает тип инструмента по ID. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Тип успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Тип не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getTypeOfToolById(
            @Parameter(description = "Артикул типа инструмента", required = true)
            @PathVariable String id) {
        try {
            // Поиск типа инструмента по идентификатору через сервис
            TypeOfTool typeOfTool = typeOfToolService.getTypeOfToolById(id);
            return ResponseEntity.ok(typeOfTool);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия типа инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении типа инструмента: " + e.getMessage());
        }
    }

    // Получение типа инструмента по названию с обработкой ошибок
    @Operation(
            summary = "Получение типа инструмента по названию",
            description = "Возвращает тип инструмента по названию. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Тип успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Тип не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getTypeOfToolByName(
            @Parameter(description = "Название типа инструмента", required = true)
            @PathVariable String name) {
        try {
            // Поиск типа инструмента по названию через сервис
            TypeOfTool typeOfTool = typeOfToolService.getTypeOfToolByName(name);
            return ResponseEntity.ok(typeOfTool);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия типа инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении типа инструмента: " + e.getMessage());
        }
    }

    // Создание нового типа инструмента с обработкой ошибок
    @Operation(
            summary = "Создание нового типа инструмента",
            description = "Создает новый тип инструмента с указанным артикулом, названием и описанием. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Тип успешно создан."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры или артикул уже существует."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> createTypeOfTool(
            @Parameter(description = "Артикул типа инструмента", required = true)
            @RequestParam String articleToolType,
            @Parameter(description = "Название типа инструмента", required = true)
            @RequestParam String name,
            @Parameter(description = "Описание типа инструмента", required = true)
            @RequestParam String description) {
        try {
            // Создание нового типа инструмента через сервис с указанным артикулом
            TypeOfTool newTypeOfTool = typeOfToolService.createTypeOfTool(articleToolType, name, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTypeOfTool);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при создании типа инструмента: " + e.getMessage());
        }
    }

    // Обновление типа инструмента по идентификатору с обработкой ошибок
    @Operation(
            summary = "Обновление типа инструмента по ID",
            description = "Обновляет тип инструмента по ID. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Тип успешно обновлен."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Тип не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTypeOfTool(
            @Parameter(description = "Артикул типа инструмента", required = true)
            @PathVariable String id,
            @Parameter(description = "Новое название (опционально)", required = false)
            @RequestParam(required = false) String name,
            @Parameter(description = "Новое описание (опционально)", required = false)
            @RequestParam(required = false) String description) {
        try {
            // Обновление типа инструмента через сервис
            TypeOfTool updatedTypeOfTool = typeOfToolService.updateTypeOfTool(id, name, description);
            return ResponseEntity.ok(updatedTypeOfTool);
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия типа инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при обновлении типа инструмента: " + e.getMessage());
        }
    }

    // Удаление типа инструмента по идентификатору с обработкой ошибок
    @Operation(
            summary = "Удаление типа инструмента по ID",
            description = "Удаляет тип инструмента по ID. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Тип успешно удален."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Тип не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTypeOfTool(
            @Parameter(description = "Артикул типа инструмента", required = true)
            @PathVariable String id) {
        try {
            // Удаление типа инструмента по идентификатору через сервис
            typeOfToolService.deleteTypeOfTool(id);
            return ResponseEntity.ok("Тип инструмента успешно удален.");
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия типа инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при удалении типа инструмента: " + e.getMessage());
        }
    }
}