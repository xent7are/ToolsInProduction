package com.backend.backendtoolsinproduction.repository;

import com.backend.backendtoolsinproduction.model.Employee;
import com.backend.backendtoolsinproduction.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Репозиторий для работы с сотрудниками, предоставляет методы для поиска и управления сотрудниками
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    // Метод для поиска сотрудника по email
    Optional<Employee> findByEmail(String email);

    // Метод для поиска сотрудника по номеру телефона
    Optional<Employee> findByPhoneNumber(String phoneNumber);

    // Метод для поиска сотрудников по фамилии
    List<Employee> findBySurname(String surname);

    // Метод для поиска сотрудников по должности
    List<Employee> findByPosition(Position position);

    // Метод для поиска сотрудника по фамилии, имени и отчеству
    Optional<Employee> findBySurnameAndNameAndPatronymic(String surname, String name, String patronymic);

    // Метод для поиска сотрудника по логину
    Optional<Employee> findByLogin(String login);
}