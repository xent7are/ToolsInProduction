package com.backend.backendtoolsinproduction.config;

import com.backend.backendtoolsinproduction.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Настройка безопасности приложения
// Определяется конфигурация Spring Security с использованием JWT и ролей
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    // Сервис для работы с сотрудниками
    private EmployeeService employeeService;

    @Autowired
    // Фильтр для проверки JWT-токенов
    private JwtRequestFilter jwtRequestFilter;

    // Создание бина для хеширования паролей
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Настройка цепочки фильтров безопасности
    // Определяются правила доступа для различных эндпоинтов на основе ролей
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Отключение CSRF, так как используется JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Разрешение доступа к эндпоинтам аутентификации и документации
                        .requestMatchers("/employees/**").hasRole("ADMIN") // Доступ только для ADMIN
                        .requestMatchers("/positions/**").hasRole("ADMIN") // Доступ только для ADMIN
                        // Эндпоинты для таблиц (доступ для ADMIN и STOREKEEPER)
                        .requestMatchers("/history-tool-issues/**").hasAnyRole("ADMIN", "STOREKEEPER") // SELECT, INSERT, DELETE на history_tool_issue
                        .requestMatchers("/history-write-off-instruments/**").hasAnyRole("ADMIN", "STOREKEEPER") // SELECT на history_write_off_instrument + процедура write_off_tool
                        .requestMatchers("/storage-locations/**").hasAnyRole("ADMIN", "STOREKEEPER") // SELECT на storage_locations
                        .requestMatchers("/tools/**").hasAnyRole("ADMIN", "STOREKEEPER") // SELECT, INSERT, DELETE, UPDATE на tools
                        .requestMatchers("/types-of-tools/**").hasAnyRole("ADMIN", "STOREKEEPER") // SELECT, INSERT, DELETE на types_of_tools
                        // Эндпоинты для представлений (доступ для всех ролей: ADMIN, STOREKEEPER, WORKER)
                        .requestMatchers("/tools/full-info", "/history-tool-issues/tools-in-use").hasAnyRole("ADMIN", "STOREKEEPER", "WORKER") // tools_full_info и tools_in_use
                        // Эндпоинты для процедур (доступ для ADMIN и STOREKEEPER)
                        .requestMatchers("/tools/batch", "/tools/add", "/tools/available/**", "/history-tool-issues/tools-in-use-by-employee/**").hasAnyRole("ADMIN", "STOREKEEPER") // Процедуры add_tool_batch, add_tool, get_available_tools_in_storage, get_tools_in_use_by_employee
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Отключение сессий, используется JWT
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Добавление JWT-фильтра перед стандартным фильтром
        return http.build();
    }
}