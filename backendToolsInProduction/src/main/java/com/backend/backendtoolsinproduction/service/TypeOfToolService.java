package com.backend.backendtoolsinproduction.service;

import com.backend.backendtoolsinproduction.model.TypeOfTool;
import com.backend.backendtoolsinproduction.repository.TypeOfToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

// Сервис для управления типами инструментов, предоставляет CRUD-операции
@Service
public class TypeOfToolService {

    // Репозиторий для работы с типами инструментов
    @Autowired
    private TypeOfToolRepository typeOfToolRepository;

    // Метод для получения всех типов инструментов
    public List<TypeOfTool> getAllTypesOfTools() {
        List<TypeOfTool> typesOfTools = typeOfToolRepository.findAll();
        if (typesOfTools.isEmpty()) {
            throw new NoSuchElementException("Типы инструментов не найдены.");
        }
        return typesOfTools;
    }

    // Метод для получения типа инструмента по ID
    public TypeOfTool getTypeOfToolById(String articleToolType) {
        return typeOfToolRepository.findById(articleToolType)
                .orElseThrow(() -> new NoSuchElementException("Тип инструмента с артикулом " + articleToolType + " не найден."));
    }

    // Метод для получения типа инструмента по названию
    public TypeOfTool getTypeOfToolByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название типа инструмента не может быть пустым.");
        }
        return typeOfToolRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Тип инструмента с названием " + name + " не найден."));
    }

    // Метод для создания нового типа инструмента
    @Transactional
    public TypeOfTool createTypeOfTool(String articleToolType, String name, String description) {
        if (articleToolType == null || articleToolType.isEmpty() || name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Все поля должны быть заполнены.");
        }
        // Проверка на существование артикула
        if (typeOfToolRepository.findById(articleToolType).isPresent()) {
            throw new IllegalArgumentException("Тип инструмента с артикулом " + articleToolType + " уже существует.");
        }
        // Проверка на существование названия
        if (typeOfToolRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Тип инструмента с названием " + name + " уже существует.");
        }
        // Проверка длины артикула
        if (articleToolType.length() > 8) {
            throw new IllegalArgumentException("Артикул не должен превышать 8 символов.");
        }

        // Создание и сохранение нового типа инструмента
        TypeOfTool newTypeOfTool = new TypeOfTool(articleToolType, name, description);
        return typeOfToolRepository.save(newTypeOfTool);
    }

    // Метод для обновления типа инструмента
    @Transactional
    public TypeOfTool updateTypeOfTool(String articleToolType, String name, String description) {
        TypeOfTool typeOfTool = typeOfToolRepository.findById(articleToolType)
                .orElseThrow(() -> new NoSuchElementException("Тип инструмента с артикулом " + articleToolType + " не найден."));

        if (name != null && !name.isEmpty()) {
            typeOfTool.setName(name);
        }
        if (description != null && !description.isEmpty()) {
            typeOfTool.setDescription(description);
        }

        return typeOfToolRepository.save(typeOfTool);
    }

    // Метод для удаления типа инструмента
    @Transactional
    public void deleteTypeOfTool(String articleToolType) {
        TypeOfTool typeOfTool = typeOfToolRepository.findById(articleToolType)
                .orElseThrow(() -> new NoSuchElementException("Тип инструмента с артикулом " + articleToolType + " не найден."));
        typeOfToolRepository.deleteById(articleToolType);
    }
}