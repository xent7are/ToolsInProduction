package com.backend.backendtoolsinproduction.repository;

import com.backend.backendtoolsinproduction.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Репозиторий для работы с должностями, предоставляет методы для поиска и управления должностями
@Repository
public interface PositionRepository extends JpaRepository<Position, String> {

    // Поиск должности по названию
    Optional<Position> findByTitlePosition(String titlePosition);

    // Поиск должностей с зарплатой больше указанного значения
    List<Position> findBySalaryGreaterThan(double salary);
}