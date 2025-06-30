package com.backend.backendtoolsinproduction.controller;

import com.backend.backendtoolsinproduction.model.Employee;
import com.backend.backendtoolsinproduction.service.EmployeeService;
import com.backend.backendtoolsinproduction.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// Контроллер для аутентификации сотрудников
// Обрабатываются запросы на вход и выдачу JWT-токена
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    // Сервис для работы с сотрудниками
    private EmployeeService employeeService;

    @Autowired
    // Компонент для хеширования паролей
    private BCryptPasswordEncoder passwordEncoder;

    // Аутентификация сотрудника
    // Проверяются учетные данные, генерируется JWT-токен
    @Operation(
            summary = "Аутентификация сотрудника",
            description = "Авторизует сотрудника по логину и паролю, возвращает JWT-токен с логином и ролью."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "JWT токен успешно создан."),
                    @ApiResponse(responseCode = "401", description = "Неверные учетные данные."),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса.")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(
            @Parameter(description = "Логин сотрудника", required = true)
            @RequestParam String login,
            @Parameter(description = "Пароль сотрудника", required = true)
            @RequestParam String password) {
        try {
            // Проверка заполненности полей
            if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Все поля должны быть заполнены.");
            }

            // Поиск сотрудника по логину
            Employee employee = employeeService.getEmployeeByLogin(login);
            if (employee == null || !passwordEncoder.matches(password, employee.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные учетные данные.");
            }

            // Генерация токена с ролью сотрудника
            String role = employee.getPosition().getRole().name();
            String jwt = JwtUtil.generateToken(login, role);

            // Формирование ответа с токеном
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при аутентификации.");
        }
    }
}