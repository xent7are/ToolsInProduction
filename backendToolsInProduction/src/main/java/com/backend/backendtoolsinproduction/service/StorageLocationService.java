package com.backend.backendtoolsinproduction.service;

import com.backend.backendtoolsinproduction.model.StorageLocation;
import com.backend.backendtoolsinproduction.repository.StorageLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

// Сервис для управления местами хранения, предоставляет CRUD-операции
@Service
public class StorageLocationService {

    // Репозиторий для работы с местами хранения
    @Autowired
    private StorageLocationRepository storageLocationRepository;

    // Метод для получения всех мест хранения
    public List<StorageLocation> getAllStorageLocations() {
        List<StorageLocation> storageLocations = storageLocationRepository.findAll();
        if (storageLocations.isEmpty()) {
            throw new NoSuchElementException("Места хранения не найдены.");
        }
        return storageLocations;
    }

    // Метод для получения места хранения по ID
    public StorageLocation getStorageLocationById(String idPlace) {
        return storageLocationRepository.findById(idPlace)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с ID " + idPlace + " не найдено."));
    }

    // Метод для получения места хранения по названию
    public StorageLocation getStorageLocationByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название места хранения не может быть пустым.");
        }
        return storageLocationRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с названием " + name + " не найдено."));
    }

    // Метод для создания нового места хранения
    @Transactional
    public StorageLocation createStorageLocation(String name, String description) {
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Все поля должны быть заполнены.");
        }
        if (storageLocationRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Место хранения с названием " + name + " уже существует.");
        }

        String newIdPlace = generateNewId("STL");
        StorageLocation newStorageLocation = new StorageLocation(newIdPlace, name, description);
        return storageLocationRepository.save(newStorageLocation);
    }

    // Метод для обновления места хранения
    @Transactional
    public StorageLocation updateStorageLocation(String idPlace, String name, String description) {
        StorageLocation storageLocation = storageLocationRepository.findById(idPlace)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с ID " + idPlace + " не найдено."));

        if (name != null && !name.isEmpty()) {
            storageLocation.setName(name);
        }
        if (description != null && !description.isEmpty()) {
            storageLocation.setDescription(description);
        }

        return storageLocationRepository.save(storageLocation);
    }

    // Метод для удаления места хранения
    @Transactional
    public void deleteStorageLocation(String idPlace) {
        StorageLocation storageLocation = storageLocationRepository.findById(idPlace)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с ID " + idPlace + " не найдено."));
        storageLocationRepository.deleteById(idPlace);
    }

    // Метод для генерации нового ID в формате "STL + число"
    private String generateNewId(String prefix) {
        List<StorageLocation> allStorageLocations = storageLocationRepository.findAll();
        int maxId = 0;
        for (StorageLocation storageLocation : allStorageLocations) {
            String id = storageLocation.getIdPlace().substring(3);
            int num = Integer.parseInt(id);
            maxId = Math.max(maxId, num);
        }
        return prefix + (maxId + 1);
    }
}