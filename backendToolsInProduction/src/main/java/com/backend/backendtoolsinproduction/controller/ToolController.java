package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.Tool;
import com.backend.backendtoolsinproduction.service.ToolService;
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

// Контроллер для управления инструментами, предоставляет CRUD-операции и дополнительные методы
@RestController
@RequestMapping("/tools")
@SecurityRequirement(name = "bearerAuth")
public class ToolController {

    @Autowired
    // Сервис для работы с инструментами
    private ToolService toolService;

    // Получение списка всех инструментов с обработкой ошибок
    @Operation(
            summary = "Получение списка всех инструментов",
            description = "Возвращает список всех инструментов. Доступно для ролей ADMIN, STOREKEEPER и WORKER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список инструментов успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllTools() {
        try {
            // Получение списка всех инструментов через сервис
            List<Tool> tools = toolService.getAllTools();
            if (tools.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Инструменты не найдены.");
            }
            return ResponseEntity.ok(tools);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструментов
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка инструментов: " + e.getMessage());
        }
    }

    // Получение списка инструментов с пагинацией
    @Operation(
            summary = "Получение списка инструментов с пагинацией",
            description = "Возвращает список инструментов с пагинацией. Доступно для ролей ADMIN, STOREKEEPER и WORKER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список инструментов успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/paginated")
    public ResponseEntity<?> getAllToolsWithPagination(
            @Parameter(description = "Номер страницы", required = false)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество записей на странице", required = false)
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Получение списка инструментов с пагинацией через сервис
            Page<Tool> toolsPage = toolService.getAllToolsWithPagination(page, size);
            if (toolsPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Инструменты не найдены.");
            }
            return ResponseEntity.ok(toolsPage);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструментов
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка инструментов с пагинацией: " + e.getMessage());
        }
    }

    // Получение инструмента по идентификатору с обработкой ошибок
    @Operation(
            summary = "Получение инструмента по ID",
            description = "Возвращает инструмент по ID. Доступно для ролей ADMIN, STOREKEEPER и WORKER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Инструмент успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструмент не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getToolById(
            @Parameter(description = "Идентификатор инструмента", required = true)
            @PathVariable String id) {
        try {
            // Поиск инструмента по идентификатору через сервис
            Tool tool = toolService.getToolById(id);
            return ResponseEntity.ok(tool);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении инструмента: " + e.getMessage());
        }
    }

    // Получение списка инструментов по типу с обработкой ошибок
    @Operation(
            summary = "Получение инструментов по типу",
            description = "Возвращает список инструментов по типу. Доступно для ролей ADMIN, STOREKEEPER и WORKER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список инструментов успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/type/{typeName}")
    public ResponseEntity<?> getToolsByType(
            @Parameter(description = "Название типа инструмента", required = true)
            @PathVariable String typeName) {
        try {
            // Получение списка инструментов по типу через сервис
            List<Tool> tools = toolService.getToolsByType(typeName);
            if (tools.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Инструменты с типом " + typeName + " не найдены.");
            }
            return ResponseEntity.ok(tools);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструментов или типа
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка инструментов: " + e.getMessage());
        }
    }

    // Получение списка инструментов по месту хранения с обработкой ошибок
    @Operation(
            summary = "Получение инструментов по месту хранения",
            description = "Возвращает список инструментов по месту хранения. Доступно для ролей ADMIN, STOREKEEPER и WORKER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список инструментов успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/storage/{storageName}")
    public ResponseEntity<?> getToolsByStorageLocation(
            @Parameter(description = "Название места хранения", required = true)
            @PathVariable String storageName) {
        try {
            // Получение списка инструментов по месту хранения через сервис
            List<Tool> tools = toolService.getToolsByStorageLocation(storageName);
            if (tools.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Инструменты в месте хранения " + storageName + " не найдены.");
            }
            return ResponseEntity.ok(tools);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструментов или места хранения
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка инструментов: " + e.getMessage());
        }
    }

    // Добавление нового инструмента с обработкой ошибок
    @Operation(
            summary = "Добавление нового инструмента",
            description = "Добавляет новый инструмент. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Инструмент успешно добавлен."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Тип или место хранения не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> addTool(
            @Parameter(description = "Артикул типа инструмента", required = true)
            @RequestParam String articleToolType,
            @Parameter(description = "Идентификатор места хранения", required = true)
            @RequestParam String idPlace) {
        try {
            // Добавление нового инструмента через сервис
            toolService.addTool(articleToolType, idPlace);
            return ResponseEntity.status(HttpStatus.CREATED).body("Инструмент успешно добавлен.");
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия типа инструмента или места хранения
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при добавлении инструмента: " + e.getMessage());
        }
    }

    // Массовое добавление инструментов с обработкой ошибок
    @Operation(
            summary = "Массовое добавление инструментов",
            description = "Добавляет несколько инструментов. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Инструменты успешно добавлены."),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Тип или место хранения не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @PostMapping("/batch")
    public ResponseEntity<?> addToolBatch(
            @Parameter(description = "Артикул типа инструмента", required = true)
            @RequestParam String articleToolType,
            @Parameter(description = "Идентификатор места хранения", required = true)
            @RequestParam String idPlace,
            @Parameter(description = "Количество инструментов", required = true)
            @RequestParam int toolCount) {
        try {
            // Массовое добавление инструментов через сервис
            toolService.addToolBatch(articleToolType, idPlace, toolCount);
            return ResponseEntity.status(HttpStatus.CREATED).body("Инструменты успешно добавлены.");
        } catch (IllegalArgumentException e) {
            // Обработка ошибки некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия типа инструмента или места хранения
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при массовом добавлении инструментов: " + e.getMessage());
        }
    }

    // Получение полной информации об инструментах с обработкой ошибок
    @Operation(
            summary = "Получение полной информации об инструментах",
            description = "Возвращает данные из представления tools_full_info. Доступно для ролей ADMIN, STOREKEEPER и WORKER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Информация успешно получена."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/full-info")
    public ResponseEntity<?> getToolsFullInfo() {
        try {
            // Получение полной информации об инструментах через сервис
            List<Object[]> toolsFullInfo = toolService.getToolsFullInfo();
            if (toolsFullInfo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Инструменты не найдены.");
            }
            return ResponseEntity.ok(toolsFullInfo);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия данных
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении полной информации об инструментах: " + e.getMessage());
        }
    }

    // Получение доступных инструментов в месте хранения с обработкой ошибок
    @Operation(
            summary = "Получение доступных инструментов в месте хранения",
            description = "Возвращает доступные инструменты через процедуру. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Список инструментов успешно получен."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструменты не найдены."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @GetMapping("/available/{idPlace}")
    public ResponseEntity<?> getAvailableToolsInStorage(
            @Parameter(description = "Идентификатор места хранения", required = true)
            @PathVariable String idPlace) {
        try {
            // Получение списка доступных инструментов в месте хранения через сервис
            List<Object[]> availableTools = toolService.getAvailableToolsInStorage(idPlace);
            if (availableTools.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Доступные инструменты в месте хранения с ID " + idPlace + " не найдены.");
            }
            return ResponseEntity.ok(availableTools);
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструментов или места хранения
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении списка доступных инструментов: " + e.getMessage());
        }
    }

    // Удаление инструмента по идентификатору с обработкой ошибок
    @Operation(
            summary = "Удаление инструмента по ID",
            description = "Удаляет инструмент по ID. Доступно для ролей ADMIN и STOREKEEPER."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Инструмент успешно удален."),
                    @ApiResponse(responseCode = "403", description = "Нет доступа."),
                    @ApiResponse(responseCode = "404", description = "Инструмент не найден."),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTool(
            @Parameter(description = "Идентификатор инструмента", required = true)
            @PathVariable String id) {
        try {
            // Удаление инструмента по идентификатору через сервис
            toolService.deleteTool(id);
            return ResponseEntity.ok("Инструмент успешно удален.");
        } catch (NoSuchElementException e) {
            // Обработка ошибки отсутствия инструмента
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Обработка внутренней ошибки сервера
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при удалении инструмента: " + e.getMessage());
        }
    }
}