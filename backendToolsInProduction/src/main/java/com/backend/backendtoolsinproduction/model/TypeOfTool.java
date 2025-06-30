package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.*;

// Модель для типа инструмента, хранит информацию о типе инструмента
@Entity
@Table(name = "types_of_tools")
public class TypeOfTool {

    @Id
    @Column(name = "article_tool_type", length = 8)
    private String articleToolType; // Артикул типа инструмента

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name; // Название типа инструмента

    @Column(name = "description", length = 200, nullable = false)
    private String description; // Описание типа инструмента

    // Конструктор по умолчанию
    public TypeOfTool() {
    }

    // Конструктор с параметрами
    public TypeOfTool(String articleToolType, String name, String description) {
        this.articleToolType = articleToolType;
        this.name = name;
        this.description = description;
    }

    // Геттеры и сеттеры

    // Метод для получения артикула типа инструмента
    public String getArticleToolType() {
        return articleToolType;
    }

    // Метод для установки артикула типа инструмента
    public void setArticleToolType(String articleToolType) {
        this.articleToolType = articleToolType;
    }

    // Метод для получения названия типа инструмента
    public String getName() {
        return name;
    }

    // Метод для установки названия типа инструмента
    public void setName(String name) {
        this.name = name;
    }

    // Метод для получения описания типа инструмента
    public String getDescription() {
        return description;
    }

    // Метод для установки описания типа инструмента
    public void setDescription(String description) {
        this.description = description;
    }
}