package com.backend.backendtoolsinproduction.service;

import com.backend.backendtoolsinproduction.model.Position;
import com.backend.backendtoolsinproduction.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.NoSuchElementException;

// Сервис для управления должностями, предоставляет CRUD-операции
@Service
public class PositionService {

    // Репозиторий для работы с должностями
    @Autowired
    private PositionRepository positionRepository;

    // Метод для получения всех должностей
    public List<Position> getAllPositions() {
        // Получение списка всех должностей
        List<Position> positions = positionRepository.findAll();
        // Проверка, что список должностей не пуст
        if (positions.isEmpty()) {
            throw new NoSuchElementException("Должности не найдены.");
        }
        return positions;
    }

    // Метод для получения должности по ID
    public Position getPositionById(String idPosition) {
        // Поиск должности по ID, выбрасывается исключение, если должность не найдена
        return positionRepository.findById(idPosition)
                .orElseThrow(() -> new NoSuchElementException("Должность с ID " + idPosition + " не найдена."));
    }

    // Метод для получения должности по названию
    public Position getPositionByTitle(String titlePosition) {
        // Проверка, что название должности указано
        if (titlePosition == null || titlePosition.isEmpty()) {
            throw new IllegalArgumentException("Название должности не может быть пустым.");
        }
        // Поиск должности по названию, выбрасывается исключение, если должность не найдена
        return positionRepository.findByTitlePosition(titlePosition)
                .orElseThrow(() -> new NoSuchElementException("Должность с названием " + titlePosition + " не найдена."));
    }

    // Метод для создания новой должности
    @Transactional
    public Position createPosition(String titlePosition, String requirements, String duties, double salary, String role) {
        // Проверка, что все поля заполнены
        if (titlePosition == null || titlePosition.isEmpty() || requirements == null || requirements.isEmpty() ||
                duties == null || duties.isEmpty() || role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Все поля должны быть заполнены.");
        }
        // Проверка корректности зарплаты
        if (salary <= 0) {
            throw new IllegalArgumentException("Зарплата должна быть больше нуля.");
        }
        // Проверка уникальности названия должности
        if (positionRepository.findByTitlePosition(titlePosition).isPresent()) {
            throw new IllegalArgumentException("Должность с названием " + titlePosition + " уже существует.");
        }
        // Проверка корректности роли
        Position.Role positionRole;
        try {
            positionRole = Position.Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Недопустимая роль: " + role + ". Доступные роли: admin, storekeeper, worker.");
        }

        // Генерация нового ID для должности
        String newIdPosition = generateNewId("PST");
        // Создание новой должности
        Position newPosition = new Position(newIdPosition, titlePosition, requirements, duties, salary, positionRole);

        try {
            // Сохранение должности в базе данных
            return positionRepository.save(newPosition);
        } catch (DataIntegrityViolationException e) {
            // Обработка нарушения уникальности названия должности
            if (e.getMessage().contains("title_position")) {
                throw new IllegalArgumentException("Должность с названием " + titlePosition + " уже существует.");
            }
            throw new IllegalArgumentException("Ошибка при сохранении должности: нарушение уникальности данных.");
        }
    }

    // Метод для обновления должности
    @Transactional
    public Position updatePosition(String idPosition, String titlePosition, String requirements, String duties, Double salary, String role) {
        // Поиск должности по ID, выбрасывается исключение, если должность не найдена
        Position position = positionRepository.findById(idPosition)
                .orElseThrow(() -> new NoSuchElementException("Должность с ID " + idPosition + " не найдена."));

        // Обновление названия должности, если указано
        if (titlePosition != null && !titlePosition.isEmpty()) {
            // Проверка уникальности нового названия должности
            if (!titlePosition.equals(position.getTitlePosition()) && positionRepository.findByTitlePosition(titlePosition).isPresent()) {
                throw new IllegalArgumentException("Должность с названием " + titlePosition + " уже существует.");
            }
            position.setTitlePosition(titlePosition);
        }
        // Обновление требований, если указано
        if (requirements != null && !requirements.isEmpty()) position.setRequirements(requirements);
        // Обновление обязанностей, если указано
        if (duties != null && !duties.isEmpty()) position.setDuties(duties);
        // Обновление зарплаты, если указано
        if (salary != null && salary > 0) position.setSalary(salary);
        // Обновление роли, если указано
        if (role != null && !role.isEmpty()) {
            // Проверка корректности роли
            try {
                position.setRole(Position.Role.valueOf(role));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Недопустимая роль: " + role + ". Доступные роли: admin, storekeeper, worker.");
            }
        }

        try {
            // Сохранение обновленной должности в базе данных
            return positionRepository.save(position);
        } catch (DataIntegrityViolationException e) {
            // Обработка нарушения уникальности названия должности
            if (e.getMessage().contains("title_position")) {
                throw new IllegalArgumentException("Должность с названием " + titlePosition + " уже существует.");
            }
            throw new IllegalArgumentException("Ошибка при обновлении должности: нарушение уникальности данных.");
        }
    }

    // Метод для удаления должности
    @Transactional
    public void deletePosition(String idPosition) {
        // Поиск должности по ID, выбрасывается исключение, если должность не найдена
        Position position = positionRepository.findById(idPosition)
                .orElseThrow(() -> new NoSuchElementException("Должность с ID " + idPosition + " не найдена."));
        // Удаление должности из базы данных
        positionRepository.deleteById(idPosition);
    }

    // Метод для генерации нового ID в формате "PST + число"
    private String generateNewId(String prefix) {
        List<Position> allPositions = positionRepository.findAll();
        int maxId = 0;
        // Цикл для определения максимального числового значения ID
        for (Position position : allPositions) {
            String id = position.getIdPosition().substring(3);
            int num = Integer.parseInt(id);
            maxId = Math.max(maxId, num);
        }
        return prefix + (maxId + 1);
    }
}