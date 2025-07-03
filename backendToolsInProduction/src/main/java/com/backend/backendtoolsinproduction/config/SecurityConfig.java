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
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/employees/**").hasRole("ADMIN")
                        .requestMatchers("/positions/**").hasRole("ADMIN")
                        .requestMatchers("/tools/full-info").hasAnyRole("ADMIN", "STOREKEEPER", "WORKER")
                        .requestMatchers("/history-tool-issues/tools-in-use").hasAnyRole("ADMIN", "STOREKEEPER", "WORKER")
                        .requestMatchers("/history-tool-issues/**").hasAnyRole("ADMIN", "STOREKEEPER")
                        .requestMatchers("/history-write-off-instruments/**").hasAnyRole("ADMIN", "STOREKEEPER")
                        .requestMatchers("/storage-locations/**").hasAnyRole("ADMIN", "STOREKEEPER")
                        .requestMatchers("/tools/**").hasAnyRole("ADMIN", "STOREKEEPER")
                        .requestMatchers("/types-of-tools/**").hasAnyRole("ADMIN", "STOREKEEPER")
                        .requestMatchers("/tools/batch", "/tools/add", "/tools/available/**",
                                "/history-tool-issues/tools-in-use-by-employee/**")
                        .hasAnyRole("ADMIN", "STOREKEEPER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}