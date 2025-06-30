package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Модель для инструмента, хранит информацию об инструменте, его типе и месте хранения
@Entity
@Table(name = "tools")
public class Tool {

    @Id
    @Column(name = "id_tool", length = 8)
    private String idTool; // ID инструмента

    @ManyToOne
    @JoinColumn(name = "article_tool_type", nullable = false)
    private TypeOfTool typeOfTool; // Тип инструмента

    @ManyToOne
    @JoinColumn(name = "id_place", nullable = false)
    private StorageLocation storageLocation; // Место хранения инструмента

    @Column(name = "availability", nullable = false)
    private boolean availability; // Доступность инструмента (1 - доступен, 0 - выдан)

    @Column(name = "date_and_time_admission", nullable = false)
    private LocalDateTime dateAndTimeAdmission; // Дата и время поступления инструмента

    // Конструктор по умолчанию
    public Tool() {
    }

    // Конструктор с параметрами
    public Tool(String idTool, TypeOfTool typeOfTool, StorageLocation storageLocation, boolean availability, LocalDateTime dateAndTimeAdmission) {
        this.idTool = idTool;
        this.typeOfTool = typeOfTool;
        this.storageLocation = storageLocation;
        this.availability = availability;
        this.dateAndTimeAdmission = dateAndTimeAdmission;
    }

    // Геттеры и сеттеры

    // Метод для получения ID инструмента
    public String getIdTool() {
        return idTool;
    }

    // Метод для установки ID инструмента
    public void setIdTool(String idTool) {
        this.idTool = idTool;
    }

    // Метод для получения типа инструмента
    public TypeOfTool getTypeOfTool() {
        return typeOfTool;
    }

    // Метод для установки типа инструмента
    public void setTypeOfTool(TypeOfTool typeOfTool) {
        this.typeOfTool = typeOfTool;
    }

    // Метод для получения места хранения инструмента
    public StorageLocation getStorageLocation() {
        return storageLocation;
    }

    // Метод для установки места хранения инструмента
    public void setStorageLocation(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    // Метод для получения доступности инструмента
    public boolean isAvailability() {
        return availability;
    }

    // Метод для установки доступности инструмента
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    // Метод для получения даты и времени поступления инструмента
    public LocalDateTime getDateAndTimeAdmission() {
        return dateAndTimeAdmission;
    }

    // Метод для установки даты и времени поступления инструмента
    public void setDateAndTimeAdmission(LocalDateTime dateAndTimeAdmission) {
        this.dateAndTimeAdmission = dateAndTimeAdmission;
    }
}