package com.backend.backendtoolsinproduction.model;

import jakarta.persistence.*;

// Модель для сотрудника, хранит информацию о сотруднике и его учетных данных
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id_employee", length = 8)
    private String idEmployee; // ID сотрудника

    @ManyToOne
    @JoinColumn(name = "id_position", nullable = false)
    private Position position; // Должность сотрудника

    @Column(name = "surname", length = 30, nullable = false)
    private String surname; // Фамилия сотрудника

    @Column(name = "name", length = 30, nullable = false)
    private String name; // Имя сотрудника

    @Column(name = "patronymic", length = 30, nullable = false)
    private String patronymic; // Отчество сотрудника

    @Column(name = "phone_number", length = 18, nullable = false)
    private String phoneNumber; // Номер телефона сотрудника

    @Column(name = "email", length = 255, nullable = false)
    private String email; // Email сотрудника

    @Column(name = "login", length = 40, nullable = false, unique = true)
    private String login; // Логин сотрудника для аутентификации

    @Column(name = "password", length = 255, nullable = false)
    private String password; // Пароль сотрудника (хешированный)

    // Конструктор по умолчанию
    public Employee() {
    }

    // Конструктор с параметрами
    public Employee(String idEmployee, Position position, String surname, String name, String patronymic,
                    String phoneNumber, String email, String login, String password) {
        this.idEmployee = idEmployee;
        this.position = position;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    // Геттеры и сеттеры
    // Метод для получения ID сотрудника
    public String getIdEmployee() {
        return idEmployee;
    }

    // Метод для установки ID сотрудника
    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    // Метод для получения должности сотрудника
    public Position getPosition() {
        return position;
    }

    // Метод для установки должности сотрудника
    public void setPosition(Position position) {
        this.position = position;
    }

    // Метод для получения фамилии сотрудника
    public String getSurname() {
        return surname;
    }

    // Метод для установки фамилии сотрудника
    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Метод для получения имени сотрудника
    public String getName() {
        return name;
    }

    // Метод для установки имени сотрудника
    public void setName(String name) {
        this.name = name;
    }

    // Метод для получения отчества сотрудника
    public String getPatronymic() {
        return patronymic;
    }

    // Метод для установки отчества сотрудника
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    // Метод для получения номера телефона сотрудника
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Метод для установки номера телефона сотрудника
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Метод для получения email сотрудника
    public String getEmail() {
        return email;
    }

    // Метод для установки email сотрудника
    public void setEmail(String email) {
        this.email = email;
    }

    // Метод для получения логина сотрудника
    public String getLogin() {
        return login;
    }

    // Метод для установки логина сотрудника
    public void setLogin(String login) {
        this.login = login;
    }

    // Метод для получения пароля сотрудника
    public String getPassword() {
        return password;
    }

    // Метод для установки пароля сотрудника
    public void setPassword(String password) {
        this.password = password;
    }
}