package com.backend.backendtoolsinproduction.util;

import java.util.regex.Pattern;

// Утилита для валидации входных данных
public class ValidationUtil {

    // Регулярное выражение для проверки формата телефона (8(XXX)XXX-XX-XX)
    private static final Pattern PHONE_PATTERN = Pattern.compile("8\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}");

    // Регулярное выражение для проверки формата email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@(mail\\.ru|yandex\\.ru|gmail\\.com)$");

    // Метод для проверки формата телефона
    public static boolean isValidPhoneFormat(String phone) {
        if (phone == null) return false;
        return PHONE_PATTERN.matcher(phone).matches();
    }

    // Метод для проверки формата email
    public static boolean isValidEmailFormat(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
}