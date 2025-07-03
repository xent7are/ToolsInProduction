package com.backend.backendtoolsinproduction.repository;

import com.backend.backendtoolsinproduction.model.HistoryWriteOffInstrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// Репозиторий для работы с историей списания инструментов, предоставляет методы для поиска и управления записями
@Repository
public interface HistoryWriteOffInstrumentRepository extends JpaRepository<HistoryWriteOffInstrument, String> {

    // Поиск записей по ID инструмента
    List<HistoryWriteOffInstrument> findByIdTool(String idTool);

    // Поиск записей по названию инструмента
    List<HistoryWriteOffInstrument> findByName(String name);

    // Поиск записей с датой списания после указанного времени с поддержкой пагинации
    @Query("SELECT hwoi FROM HistoryWriteOffInstrument hwoi WHERE hwoi.dateAndTimeWriteOff > :minTime")
    Page<HistoryWriteOffInstrument> findByDateAndTimeWriteOffAfter(@Param("minTime") LocalDateTime minTime, Pageable pageable);
}