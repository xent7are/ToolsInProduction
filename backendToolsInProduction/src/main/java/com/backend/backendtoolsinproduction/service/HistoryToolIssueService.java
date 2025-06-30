package com.backend.backendtoolsinproduction.service;

import com.backend.backendtoolsinproduction.model.Employee;
import com.backend.backendtoolsinproduction.model.HistoryToolIssue;
import com.backend.backendtoolsinproduction.model.HistoryToolIssueId;
import com.backend.backendtoolsinproduction.model.Tool;
import com.backend.backendtoolsinproduction.repository.EmployeeRepository;
import com.backend.backendtoolsinproduction.repository.HistoryToolIssueRepository;
import com.backend.backendtoolsinproduction.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

// Сервис для управления историей выдачи инструментов, предоставляет CRUD-операции и вызов представлений и процедур
@Service
public class HistoryToolIssueService {

    // Репозиторий для работы с историей выдачи инструментов
    @Autowired
    private HistoryToolIssueRepository historyToolIssueRepository;

    // Репозиторий для работы с сотрудниками
    @Autowired
    private EmployeeRepository employeeRepository;

    // Репозиторий для работы с инструментами
    @Autowired
    private ToolRepository toolRepository;

    // EntityManager для выполнения нативных SQL-запросов
    @PersistenceContext
    private EntityManager entityManager;

    // Метод для получения всех записей истории выдачи
    public List<HistoryToolIssue> getAllHistoryToolIssues() {
        // Получение списка всех записей истории выдачи
        List<HistoryToolIssue> historyToolIssues = historyToolIssueRepository.findAll();
        // Проверка, что список записей не пуст
        if (historyToolIssues.isEmpty()) {
            throw new NoSuchElementException("Записи истории выдачи инструментов не найдены.");
        }
        return historyToolIssues;
    }

    // Метод для получения записей истории выдачи с пагинацией
    public Page<HistoryToolIssue> getAllHistoryToolIssuesWithPagination(int page, int size) {
        // Создается объект Pageable с сортировкой по дате и времени выдачи в порядке возрастания
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id.dateAndTimeIssue"));
        // Получение записей истории выдачи с пагинацией
        Page<HistoryToolIssue> historyToolIssues = historyToolIssueRepository.findAll(pageable);
        // Проверка, что список записей не пуст
        if (historyToolIssues.isEmpty()) {
            throw new NoSuchElementException("Записи истории выдачи инструментов не найдены.");
        }
        return historyToolIssues;
    }

    // Метод для получения записей истории выдачи по сотруднику
    public List<HistoryToolIssue> getHistoryToolIssuesByEmployee(String idEmployee) {
        // Поиск сотрудника по ID, выбрасывается исключение, если сотрудник не найден
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new NoSuchElementException("Сотрудник с ID " + idEmployee + " не найден."));
        // Получение записей истории выдачи для указанного сотрудника
        List<HistoryToolIssue> historyToolIssues = historyToolIssueRepository.findByEmployee(employee);
        // Проверка, что список записей не пуст
        if (historyToolIssues.isEmpty()) {
            throw new NoSuchElementException("Записи истории выдачи для сотрудника с ID " + idEmployee + " не найдены.");
        }
        return historyToolIssues;
    }

    // Метод для получения записей истории выдачи по инструменту
    public List<HistoryToolIssue> getHistoryToolIssuesByTool(String idTool) {
        // Поиск инструмента по ID, выбрасывается исключение, если инструмент не найден
        Tool tool = toolRepository.findById(idTool)
                .orElseThrow(() -> new NoSuchElementException("Инструмент с ID " + idTool + " не найден."));
        // Получение записей истории выдачи для указанного инструмента
        List<HistoryToolIssue> historyToolIssues = historyToolIssueRepository.findByTool(tool);
        // Проверка, что список записей не пуст
        if (historyToolIssues.isEmpty()) {
            throw new NoSuchElementException("Записи истории выдачи для инструмента с ID " + idTool + " не найдены.");
        }
        return historyToolIssues;
    }

    // Метод для создания новой записи выдачи инструмента
    @Transactional
    public HistoryToolIssue createHistoryToolIssue(String idEmployee, String idTool, String action) {
        // Проверка, что все поля заполнены
        if (idEmployee == null || idEmployee.isEmpty() || idTool == null || idTool.isEmpty() || action == null || action.isEmpty()) {
            throw new IllegalArgumentException("Все поля должны быть заполнены.");
        }
        // Проверка корректности действия
        if (!action.equals("Выдан") && !action.equals("Возврат")) {
            throw new IllegalArgumentException("Недопустимое значение действия. Допустимые значения: 'Выдан', 'Возврат'.");
        }

        // Поиск сотрудника по ID, выбрасывается исключение, если сотрудник не найден
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new NoSuchElementException("Сотрудник с ID " + idEmployee + " не найден."));
        // Поиск инструмента по ID, выбрасывается исключение, если инструмент не найден
        Tool tool = toolRepository.findById(idTool)
                .orElseThrow(() -> new NoSuchElementException("Инструмент с ID " + idTool + " не найден."));

        // Проверка доступности инструмента для выдачи
        if (action.equals("Выдан") && !tool.isAvailability()) {
            throw new IllegalStateException("Инструмент уже выдан.");
        }
        // Проверка возможности возврата инструмента
        if (action.equals("Возврат") && tool.isAvailability()) {
            throw new IllegalStateException("Инструмент уже находится на складе.");
        }

        // Создание составного ключа для записи истории выдачи
        HistoryToolIssueId id = new HistoryToolIssueId(idEmployee, idTool, LocalDateTime.now());
        // Создание новой записи истории выдачи
        HistoryToolIssue historyToolIssue = new HistoryToolIssue(id, employee, tool, action);
        // Сохранение записи в базе данных
        return historyToolIssueRepository.save(historyToolIssue);
    }

    // Метод для удаления записи истории выдачи
    @Transactional
    public void deleteHistoryToolIssue(String idEmployee, String idTool, String dateAndTimeIssue) {
        // Парсинг строки с датой и временем
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateAndTimeIssue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты и времени. Используйте 'yyyy-MM-dd HH:mm:ss'.");
        }

        // Создание составного ключа для записи истории выдачи
        HistoryToolIssueId id = new HistoryToolIssueId(idEmployee, idTool, dateTime);
        // Поиск записи по ключу, выбрасывается исключение, если запись не найдена
        HistoryToolIssue historyToolIssue = historyToolIssueRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Запись истории выдачи не найдена."));
        // Удаление записи из базы данных
        historyToolIssueRepository.deleteById(id);
    }

    // Метод для получения информации об инструментах в использовании из представления
    public List<Object[]> getToolsInUse() {
        // Выполнение нативного SQL-запроса к представлению tools_in_use
        Query query = entityManager.createNativeQuery(
                "SELECT tool_id, tool_type_name, surname, name, patronymic, title_position, phone_number, email, issuance_date " +
                        "FROM tools_in_use");
        // Получение результатов запроса
        List<Object[]> results = query.getResultList();
        // Проверка, что список результатов не пуст
        if (results.isEmpty()) {
            throw new NoSuchElementException("Инструменты в использовании не найдены.");
        }
        return results;
    }

    // Метод для получения инструментов, используемых сотрудником, с использованием хранимой процедуры
    public List<Object[]> getToolsInUseByEmployee(String idEmployee) {
        // Выполнение хранимой процедуры get_tools_in_use_by_employee
        Query query = entityManager.createNativeQuery(
                "CALL get_tools_in_use_by_employee(:idEmployee)");
        // Установка параметра ID сотрудника
        query.setParameter("idEmployee", idEmployee);
        // Получение результатов запроса
        List<Object[]> results = query.getResultList();
        // Проверка, что список результатов не пуст
        if (results.isEmpty()) {
            throw new NoSuchElementException("Инструменты, используемые сотрудником с ID " + idEmployee + ", не найдены.");
        }
        return results;
    }
}