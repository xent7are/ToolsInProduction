package com.backend.backendtoolsinproduction.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Утилита для работы с JWT-токенами, предоставляет методы генерации и валидации токенов
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 часов

    // Генерация токена с логином и ролью
    public static String generateToken(String login, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, login);
    }

    // Создание токена
    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Извлечение логина из токена
    public static String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Извлечение роли из токена
    public static String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Универсальный метод извлечения данных
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Извлечение всех данных из токена
    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Проверка валидности токена
    public static boolean validateToken(String token, String login) {
        final String extractedLogin = extractLogin(token);
        return (extractedLogin.equals(login) && !isTokenExpired(token));
    }

    // Проверка срока действия токена
    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Извлечение даты истечения срока действия
    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}