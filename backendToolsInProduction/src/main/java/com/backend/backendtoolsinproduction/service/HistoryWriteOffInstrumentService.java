package com.backend.backendtoolsinproduction.service;

import com.backend.backendtoolsinproduction.model.HistoryWriteOffInstrument;
import com.backend.backendtoolsinproduction.repository.HistoryWriteOffInstrumentRepository;
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
import java.util.List;
import java.util.NoSuchElementException;

// Сервис для управления историей списания инструментов, предоставляет CRUD-операции и вызов хранимых процедур
@Service
public class HistoryWriteOffInstrumentService {

    // Репозиторий для работы с историей списания инструментов
    @Autowired
    private HistoryWriteOffInstrumentRepository historyWriteOffInstrumentRepository;

    // Репозиторий для работы с инструментами
    @Autowired
    private ToolRepository toolRepository;

    // EntityManager для выполнения нативных SQL-запросов
    @PersistenceContext
    private EntityManager entityManager;

    // Метод для получения всех записей истории списания
    public List<HistoryWriteOffInstrument> getAllHistoryWriteOffInstruments() {
        List<HistoryWriteOffInstrument> historyWriteOffInstruments = historyWriteOffInstrumentRepository.findAll();
        if (historyWriteOffInstruments.isEmpty()) {
            throw new NoSuchElementException("Записи истории списания инструментов не найдены.");
        }
        return historyWriteOffInstruments;
    }

    // Метод для получения записей истории списания с пагинацией
    public Page<HistoryWriteOffInstrument> getAllHistoryWriteOffInstrumentsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "dateAndTimeWriteOff"));
        Page<HistoryWriteOffInstrument> historyWriteOffInstruments = historyWriteOffInstrumentRepository.findAll(pageable);
        if (historyWriteOffInstruments.isEmpty()) {
            throw new NoSuchElementException("Записи истории списания инструментов не найдены.");
        }
        return historyWriteOffInstruments;
    }

    // Метод для получения записи истории списания по ID
    public HistoryWriteOffInstrument getHistoryWriteOffInstrumentById(String idWriteOff) {
        return historyWriteOffInstrumentRepository.findById(idWriteOff)
                .orElseThrow(() -> new NoSuchElementException("Запись списания с ID " + idWriteOff + " не найдена."));
    }

    // Метод для списания инструмента с использованием хранимой процедуры write_off_tool
    @Transactional
    public void writeOffTool(String idTool) {
        toolRepository.findById(idTool)
                .orElseThrow(() -> new NoSuchElementException("Инструмент с ID " + idTool + " не найден."));
        Query query = entityManager.createNativeQuery("CALL write_off_tool(:idTool)");
        query.setParameter("idTool", idTool);
        query.executeUpdate();
    }
}