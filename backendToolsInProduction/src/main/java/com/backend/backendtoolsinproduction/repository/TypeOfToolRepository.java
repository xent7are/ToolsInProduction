package com.backend.backendtoolsinproduction.repository;

import com.backend.backendtoolsinproduction.model.TypeOfTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Репозиторий для работы с типами инструментов, предоставляет методы для поиска и управления типами инструментов
@Repository
public interface TypeOfToolRepository extends JpaRepository<TypeOfTool, String> {

    // Поиск типа инструмента по названию
    Optional<TypeOfTool> findByName(String name);
}
