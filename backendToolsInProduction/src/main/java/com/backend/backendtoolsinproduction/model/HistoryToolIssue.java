package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Модель для истории выдачи инструментов, хранит информацию о выдаче и возврате инструментов
@Entity
@Table(name = "history_tool_issue")
public class HistoryToolIssue {

    @EmbeddedId
    private HistoryToolIssueId id; // Составной первичный ключ

    @ManyToOne
    @MapsId("employee")
    @JoinColumn(name = "id_employee", nullable = false)
    private Employee employee; // Сотрудник, связанный с выдачей

    @ManyToOne
    @MapsId("tool")
    @JoinColumn(name = "id_tool", nullable = false)
    private Tool tool; // Инструмент, связанный с выдачей

    @Column(name = "action", length = 50, nullable = false)
    private String action; // Действие (Выдан или Возврат)

    // Конструктор по умолчанию
    public HistoryToolIssue() {
    }

    // Конструктор с параметрами
    public HistoryToolIssue(HistoryToolIssueId id, Employee employee, Tool tool, String action) {
        this.id = id;
        this.employee = employee;
        this.tool = tool;
        this.action = action;
    }

    // Геттеры и сеттеры

    // Метод для получения составного первичного ключа
    public HistoryToolIssueId getId() {
        return id;
    }

    // Метод для установки составного первичного ключа
    public void setId(HistoryToolIssueId id) {
        this.id = id;
    }

    // Метод для получения связанного сотрудника
    public Employee getEmployee() {
        return employee;
    }

    // Метод для установки связанного сотрудника
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Метод для получения связанного инструмента
    public Tool getTool() {
        return tool;
    }

    // Метод для установки связанного инструмента
    public void setTool(Tool tool) {
        this.tool = tool;
    }

    // Метод для получения действия (Выдан или Возврат)
    public String getAction() {
        return action;
    }

    // Метод для установки действия (Выдан или Возврат)
    public void setAction(String action) {
        this.action = action;
    }
}