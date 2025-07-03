package com.backend.backendtoolsinproduction.config;

import com.backend.backendtoolsinproduction.service.EmployeeService;
import com.backend.backendtoolsinproduction.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Фильтр для обработки JWT-токенов
// Проверяется наличие и валидность токена, устанавливается аутентификация
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    // Сервис для работы с сотрудниками
    private EmployeeService employeeService;

    // Фильтрация запросов
    // Извлекается и проверяется JWT-токен, устанавливается контекст безопасности
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String login = null;
        String jwt = null;

        // Проверка наличия заголовка Authorization и его формата
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Извлечение токена без префикса "Bearer "
            login = JwtUtil.extractLogin(jwt); // Извлечение логина из токена
        }

        // Установка аутентификации, если токен валиден и контекст пуст
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = employeeService.loadUserByUsername(login); // Загрузка данных пользователя
            if (JwtUtil.validateToken(jwt, login)) { // Проверка валидности токена
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Установка деталей запроса
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Установка контекста безопасности
            }
        }
        // Продолжение цепочки фильтров
        chain.doFilter(request, response);
    }
}