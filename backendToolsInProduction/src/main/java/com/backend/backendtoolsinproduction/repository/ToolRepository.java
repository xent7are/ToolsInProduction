package com.backend.backendtoolsinproduction.repository;

import com.backend.backendtoolsinproduction.model.StorageLocation;
import com.backend.backendtoolsinproduction.model.Tool;
import com.backend.backendtoolsinproduction.model.TypeOfTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// Репозиторий для работы с инструментами, предоставляет методы для поиска и управления инструментами
@Repository
public interface ToolRepository extends JpaRepository<Tool, String> {

    // Поиск инструментов по типу (через связанную сущность)
    List<Tool> findByTypeOfTool(TypeOfTool typeOfTool);

    // Поиск инструментов по месту хранения (через связанную сущность)
    List<Tool> findByStorageLocation(StorageLocation storageLocation);

    // Поиск инструментов по доступности
    List<Tool> findByAvailability(boolean availability);

    // Поиск инструментов по типу и доступности
    List<Tool> findByTypeOfToolAndAvailability(TypeOfTool typeOfTool, boolean availability);

    // Поиск инструментов с датой поступления после указанного времени с поддержкой пагинации
    @Query("SELECT t FROM Tool t WHERE t.dateAndTimeAdmission > :minTime")
    Page<Tool> findByDateAndTimeAdmissionAfter(@Param("minTime") LocalDateTime minTime, Pageable pageable);
}
