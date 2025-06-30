package com.backend.backendtoolsinproduction.service;

import com.backend.backendtoolsinproduction.model.Employee;
import com.backend.backendtoolsinproduction.model.Position;
import com.backend.backendtoolsinproduction.repository.EmployeeRepository;
import com.backend.backendtoolsinproduction.repository.PositionRepository;
import com.backend.backendtoolsinproduction.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

// Сервис для управления сотрудниками, предоставляет CRUD-операции и реализует UserDetailsService
@Service
public class EmployeeService implements UserDetailsService {

    // Репозиторий для работы с сотрудниками
    @Autowired
    private EmployeeRepository employeeRepository;

    // Репозиторий для работы с должностями
    @Autowired
    private PositionRepository positionRepository;

    // Компонент для хеширования паролей
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Метод для загрузки данных сотрудника по логину для аутентификации
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Поиск сотрудника по логину, выбрасывается исключение, если сотрудник не найден
        Employee employee = employeeRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Сотрудник с логином " + login + " не найден."));
        // Создание объекта UserDetails с логином, паролем и ролью сотрудника
        return User.withUsername(employee.getLogin())
                .password(employee.getPassword())
                .roles(employee.getPosition().getRole().name().toUpperCase())
                .build();
    }

    // Метод для получения всех сотрудников
    public List<Employee> getAllEmployees() {
        // Получение списка всех сотрудников из репозитория
        List<Employee> employees = employeeRepository.findAll();
        // Проверка, что список сотрудников не пуст
        if (employees.isEmpty()) {
            throw new NoSuchElementException("Сотрудники не найдены.");
        }
        return employees;
    }

    // Метод для получения сотрудника по ID
    public Employee getEmployeeById(String idEmployee) {
        // Поиск сотрудника по ID, выбрасывается исключение, если сотрудник не найден
        return employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new NoSuchElementException("Сотрудник с ID " + idEmployee + " не найден."));
    }

    // Метод для получения сотрудника по email
    public Employee getEmployeeByEmail(String email) {
        // Проверка корректности формата email
        if (!ValidationUtil.isValidEmailFormat(email)) {
            throw new IllegalArgumentException("Неверный формат email.");
        }
        // Поиск сотрудника по email, выбрасывается исключение, если сотрудник не найден
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Сотрудник с email " + email + " не найден."));
    }

    // Метод для получения сотрудника по номеру телефона
    public Employee getEmployeeByPhoneNumber(String phoneNumber) {
        // Проверка корректности формата номера телефона
        if (!ValidationUtil.isValidPhoneFormat(phoneNumber)) {
            throw new IllegalArgumentException("Неверный формат телефона.");
        }
        // Поиск сотрудника по номеру телефона, выбрасывается исключение, если сотрудник не найден
        return employeeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoSuchElementException("Сотрудник с номером телефона " + phoneNumber + " не найден."));
    }

    // Метод для получения сотрудников по должности
    public List<Employee> getEmployeesByPosition(String titlePosition) {
        // Поиск должности по названию, выбрасывается исключение, если должность не найдена
        Position position = positionRepository.findByTitlePosition(titlePosition)
                .orElseThrow(() -> new NoSuchElementException("Должность с названием " + titlePosition + " не найдена."));
        // Получение списка сотрудников по должности
        List<Employee> employees = employeeRepository.findByPosition(position);
        // Проверка, что список сотрудников не пуст
        if (employees.isEmpty()) {
            throw new NoSuchElementException("Сотрудники с должностью " + titlePosition + " не найдены.");
        }
        return employees;
    }

    // Метод для получения сотрудника по логину
    public Employee getEmployeeByLogin(String login) {
        // Поиск сотрудника по логину, возвращается null, если сотрудник не найден
        return employeeRepository.findByLogin(login)
                .orElse(null);
    }

    // Метод для создания нового сотрудника
    @Transactional
    public Employee createEmployee(String positionId, String surname, String name, String patronymic,
                                   String phoneNumber, String email, String login, String password) {
        // Проверка, что все поля заполнены
        if (positionId == null || positionId.isEmpty() || surname == null || surname.isEmpty() ||
                name == null || name.isEmpty() || patronymic == null || patronymic.isEmpty() ||
                phoneNumber == null || phoneNumber.isEmpty() || email == null || email.isEmpty() ||
                login == null || login.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Все поля должны быть заполнены.");
        }

        // Проверка корректности формата номера телефона
        if (!ValidationUtil.isValidPhoneFormat(phoneNumber)) {
            throw new IllegalArgumentException("Неверный формат телефона.");
        }

        // Проверка корректности формата email
        if (!ValidationUtil.isValidEmailFormat(email)) {
            throw new IllegalArgumentException("Неверный формат email.");
        }

        // Проверка уникальности номера телефона
        if (employeeRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new IllegalArgumentException("Сотрудник с таким номером телефона уже существует.");
        }

        // Проверка уникальности email
        if (employeeRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Сотрудник с таким email уже существует.");
        }

        // Проверка уникальности логина
        if (employeeRepository.findByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Сотрудник с таким логином уже существует.");
        }

        // Поиск должности по ID, выбрасывается исключение, если должность не найдена
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new NoSuchElementException("Должность с ID " + positionId + " не найдена."));

        // Генерация нового ID для сотрудника
        String newIdEmployee = generateNewId("EMP");
        // Хеширование пароля
        String hashedPassword = passwordEncoder.encode(password);
        // Создание объекта сотрудника
        Employee newEmployee = new Employee(newIdEmployee, position, surname, name, patronymic, phoneNumber, email, login, hashedPassword);
        // Сохранение сотрудника в базе данных
        return employeeRepository.save(newEmployee);
    }

    // Метод для обновления данных сотрудника
    @Transactional
    public Employee updateEmployee(String idEmployee, String positionId, String surname, String name, String patronymic,
                                   String phoneNumber, String email, String login, String password) {
        // Поиск сотрудника по ID, выбрасывается исключение, если сотрудник не найден
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new NoSuchElementException("Сотрудник с ID " + idEmployee + " не найден."));

        // Обновление должности, если указано
        if (positionId != null && !positionId.isEmpty()) {
            // Поиск должности по ID, выбрасывается исключение, если должность не найдена
            Position position = positionRepository.findById(positionId)
                    .orElseThrow(() -> new NoSuchElementException("Должность с ID " + positionId + " не найдена."));
            employee.setPosition(position);
        }
        // Обновление фамилии, если указано
        if (surname != null && !surname.isEmpty()) employee.setSurname(surname);
        // Обновление имени, если указано
        if (name != null && !name.isEmpty()) employee.setName(name);
        // Обновление отчества, если указано
        if (patronymic != null && !patronymic.isEmpty()) employee.setPatronymic(patronymic);
        // Обновление номера телефона, если указано
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            // Проверка корректности формата номера телефона
            if (!ValidationUtil.isValidPhoneFormat(phoneNumber)) {
                throw new IllegalArgumentException("Неверный формат телефона.");
            }
            employee.setPhoneNumber(phoneNumber);
        }
        // Обновление email, если указано
        if (email != null && !email.isEmpty()) {
            // Проверка корректности формата email
            if (!ValidationUtil.isValidEmailFormat(email)) {
                throw new IllegalArgumentException("Неверный формат email.");
            }
            employee.setEmail(email);
        }
        // Обновление логина, если указано
        if (login != null && !login.isEmpty()) employee.setLogin(login);
        // Обновление пароля, если указано
        if (password != null && !password.isEmpty()) {
            // Хеширование нового пароля
            employee.setPassword(passwordEncoder.encode(password));
        }

        // Сохранение обновленного сотрудника в базе данных
        return employeeRepository.save(employee);
    }

    // Метод для удаления сотрудника по ID
    @Transactional
    public void deleteEmployee(String idEmployee) {
        // Поиск сотрудника по ID, выбрасывается исключение, если сотрудник не найден
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new NoSuchElementException("Сотрудник с ID " + idEmployee + " не найден."));
        // Удаление сотрудника из базы данных
        employeeRepository.deleteById(idEmployee);
    }

    // Метод для генерации нового ID в формате "EMP + число"
    private String generateNewId(String prefix) {
        List<Employee> allEmployees = employeeRepository.findAll();
        int maxId = 0;
        // Цикл для определения максимального числового значения ID
        for (Employee employee : allEmployees) {
            String id = employee.getIdEmployee().substring(3);
            int num = Integer.parseInt(id);
            maxId = Math.max(maxId, num);
        }
        return prefix + (maxId + 1);
    }
}