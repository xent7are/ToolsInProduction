package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Модель для истории списания инструментов, хранит информацию о списанных инструментах
@Entity
@Table(name = "history_write_off_instrument")
public class HistoryWriteOffInstrument {

    @Id
    @Column(name = "id_write_off", length = 8)
    private String idWriteOff; // ID списания

    @Column(name = "id_tool", length = 8, nullable = false)
    private String idTool; // ID списанного инструмента

    @Column(name = "name", length = 50, nullable = false)
    private String name; // Название инструмента

    @Column(name = "date_and_time_write_off", nullable = false)
    private LocalDateTime dateAndTimeWriteOff; // Дата и время списания

    // Конструктор по умолчанию
    public HistoryWriteOffInstrument() {
    }

    // Конструктор с параметрами
    public HistoryWriteOffInstrument(String idWriteOff, String idTool, String name, LocalDateTime dateAndTimeWriteOff) {
        this.idWriteOff = idWriteOff;
        this.idTool = idTool;
        this.name = name;
        this.dateAndTimeWriteOff = dateAndTimeWriteOff;
    }

    // Геттеры и сеттеры

    // Метод для получения ID списания
    public String getIdWriteOff() {
        return idWriteOff;
    }

    // Метод для установки ID списания
    public void setIdWriteOff(String idWriteOff) {
        this.idWriteOff = idWriteOff;
    }

    // Метод для получения ID списанного инструмента
    public String getIdTool() {
        return idTool;
    }

    // Метод для установки ID списанного инструмента
    public void setIdTool(String idTool) {
        this.idTool = idTool;
    }

    // Метод для получения названия инструмента
    public String getName() {
        return name;
    }

    // Метод для установки названия инструмента
    public void setName(String name) {
        this.name = name;
    }

    // Метод для получения даты и времени списания
    public LocalDateTime getDateAndTimeWriteOff() {
        return dateAndTimeWriteOff;
    }

    // Метод для установки даты и времени списания
    public void setDateAndTimeWriteOff(LocalDateTime dateAndTimeWriteOff) {
        this.dateAndTimeWriteOff = dateAndTimeWriteOff;
    }
}