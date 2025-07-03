package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.*;

// Модель для места хранения, хранит информацию о местах хранения инструментов
@Entity
@Table(name = "storage_locations")
public class StorageLocation {

    @Id
    @Column(name = "id_place", length = 8)
    private String idPlace; // ID места хранения

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name; // Название места хранения

    @Column(name = "description", length = 200, nullable = false)
    private String description; // Описание места хранения

    // Конструктор по умолчанию
    public StorageLocation() {
    }

    // Конструктор с параметрами
    public StorageLocation(String idPlace, String name, String description) {
        this.idPlace = idPlace;
        this.name = name;
        this.description = description;
    }

    // Геттеры и сеттеры

    // Метод для получения ID места хранения
    public String getIdPlace() {
        return idPlace;
    }

    // Метод для установки ID места хранения
    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    // Метод для получения названия места хранения
    public String getName() {
        return name;
    }

    // Метод для установки названия места хранения
    public void setName(String name) {
        this.name = name;
    }

    // Метод для получения описания места хранения
    public String getDescription() {
        return description;
    }

    // Метод для установки описания места хранения
    public void setDescription(String description) {
        this.description = description;
    }
}