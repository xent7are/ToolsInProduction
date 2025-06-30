package com.backend.backendtoolsinproduction.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

// Конфигурация Swagger для документации API
// Определяет общую информацию об API и схему безопасности для использования JWT-токенов
@OpenAPIDefinition(
        info = @Info(
                title = "Tool Management System API",
                description = "API для управления инструментами в производственной среде. " +
                        "Позволяет управлять сотрудниками, инструментами, местами хранения, типами инструментов, " +
                        "а также вести учет выдачи и списания инструментов.",
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
        // Конфигурация для Swagger
}