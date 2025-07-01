package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.*;

// Модель для должности, хранит информацию о должности, требованиях, обязанностях, зарплате и роли
@Entity
@Table(name = "positions")
public class Position {

    @Id
    @Column(name = "id_position", length = 8)
    private String idPosition; // ID должности

    @Column(name = "title_position", length = 80, nullable = false, unique = true)
    private String titlePosition; // Название должности

    @Column(name = "requirements", nullable = false, columnDefinition = "TEXT")
    private String requirements; // Требования к должности

    @Column(name = "duties", nullable = false, columnDefinition = "TEXT")
    private String duties; // Обязанности по должности

    @Column(name = "salary", nullable = false)
    private double salary; // Зарплата по должности

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // Роль для авторизации (admin, storekeeper, worker)

    // Перечисление для ролей
    public enum Role {
        admin, storekeeper, worker
    }

    // Конструктор по умолчанию
    public Position() {
    }

    // Конструктор с параметрами
    public Position(String idPosition, String titlePosition, String requirements, String duties, double salary, Role role) {
        this.idPosition = idPosition;
        this.titlePosition = titlePosition;
        this.requirements = requirements;
        this.duties = duties;
        this.salary = salary;
        this.role = role;
    }

    // Геттеры и сеттеры
    // Метод для получения ID должности
    public String getIdPosition() {
        return idPosition;
    }

    // Метод для установки ID должности
    public void setIdPosition(String idPosition) {
        this.idPosition = idPosition;
    }

    // Метод для получения названия должности
    public String getTitlePosition() {
        return titlePosition;
    }

    // Метод для установки названия должности
    public void setTitlePosition(String titlePosition) {
        this.titlePosition = titlePosition;
    }

    // Метод для получения требований к должности
    public String getRequirements() {
        return requirements;
    }

    // Метод для установки требований к должности
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    // Метод для получения обязанностей по должности
    public String getDuties() {
        return duties;
    }

    // Метод для установки обязанностей по должности
    public void setDuties(String duties) {
        this.duties = duties;
    }

    // Метод для получения зарплаты по должности
    public double getSalary() {
        return salary;
    }

    // Метод для установки зарплаты по должности
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Метод для получения роли для авторизации
    public Role getRole() {
        return role;
    }

    // Метод для установки роли для авторизации
    public void setRole(Role role) {
        this.role = role;
    }
}