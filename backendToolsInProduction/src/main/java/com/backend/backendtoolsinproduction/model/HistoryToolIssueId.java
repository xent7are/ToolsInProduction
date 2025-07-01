package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

// Класс для составного первичного ключа таблицы history_tool_issue
@Embeddable
public class HistoryToolIssueId implements Serializable {

    private String employee; // ID сотрудника
    private String tool; // ID инструмента
    private LocalDateTime dateAndTimeIssue; // Дата и время выдачи или возврата

    // Конструктор по умолчанию
    public HistoryToolIssueId() {
    }

    // Конструктор с параметрами
    public HistoryToolIssueId(String employee, String tool, LocalDateTime dateAndTimeIssue) {
        this.employee = employee;
        this.tool = tool;
        this.dateAndTimeIssue = dateAndTimeIssue;
    }

    // Геттеры и сеттеры

    // Метод для получения ID сотрудника
    public String getEmployee() {
        return employee;
    }

    // Метод для установки ID сотрудника
    public void setEmployee(String employee) {
        this.employee = employee;
    }

    // Метод для получения ID инструмента
    public String getTool() {
        return tool;
    }

    // Метод для установки ID инструмента
    public void setTool(String tool) {
        this.tool = tool;
    }

    // Метод для получения даты и времени выдачи или возврата
    public LocalDateTime getDateAndTimeIssue() {
        return dateAndTimeIssue;
    }

    // Метод для установки даты и времени выдачи или возврата
    public void setDateAndTimeIssue(LocalDateTime dateAndTimeIssue) {
        this.dateAndTimeIssue = dateAndTimeIssue;
    }

    // Реализация equals и hashCode для корректной работы составного ключа
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryToolIssueId that = (HistoryToolIssueId) o;
        return employee.equals(that.employee) &&
                tool.equals(that.tool) &&
                dateAndTimeIssue.equals(that.dateAndTimeIssue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, tool, dateAndTimeIssue);
    }
}