package com.backend.backendtoolsinproduction.repository;

import com.backend.backendtoolsinproduction.model.Employee;
import com.backend.backendtoolsinproduction.model.HistoryToolIssue;
import com.backend.backendtoolsinproduction.model.HistoryToolIssueId;
import com.backend.backendtoolsinproduction.model.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// Репозиторий для работы с историей выдачи инструментов, предоставляет методы для поиска и управления записями
@Repository
public interface HistoryToolIssueRepository extends JpaRepository<HistoryToolIssue, HistoryToolIssueId> {

    // Поиск записей по сотруднику (через связанную сущность)
    List<HistoryToolIssue> findByEmployee(Employee employee);

    // Поиск записей по инструменту (через связанную сущность)
    List<HistoryToolIssue> findByTool(Tool tool);

    // Поиск записей по действию (Выдан или Возврат)
    List<HistoryToolIssue> findByAction(String action);

    // Поиск записей по сотруднику и инструменту
    List<HistoryToolIssue> findByEmployeeAndTool(Employee employee, Tool tool);

    // Поиск записей с датой выдачи после указанного времени с поддержкой пагинации
    @Query("SELECT hti FROM HistoryToolIssue hti WHERE hti.id.dateAndTimeIssue > :minTime")
    Page<HistoryToolIssue> findByDateAndTimeIssueAfter(@Param("minTime") LocalDateTime minTime, Pageable pageable);
}
