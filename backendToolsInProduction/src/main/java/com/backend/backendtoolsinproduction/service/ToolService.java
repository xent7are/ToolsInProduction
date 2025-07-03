package com.backend.backendtoolsinproduction.service;

import com.backend.backendtoolsinproduction.model.StorageLocation;
import com.backend.backendtoolsinproduction.model.Tool;
import com.backend.backendtoolsinproduction.model.TypeOfTool;
import com.backend.backendtoolsinproduction.repository.StorageLocationRepository;
import com.backend.backendtoolsinproduction.repository.ToolRepository;
import com.backend.backendtoolsinproduction.repository.TypeOfToolRepository;
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

// Сервис для управления инструментами, предоставляет CRUD-операции и вызов хранимых процедур и представлений
@Service
public class ToolService {

    // Репозиторий для работы с инструментами
    @Autowired
    private ToolRepository toolRepository;

    // Репозиторий для работы с типами инструментов
    @Autowired
    private TypeOfToolRepository typeOfToolRepository;

    // Репозиторий для работы с местами хранения
    @Autowired
    private StorageLocationRepository storageLocationRepository;

    // EntityManager для выполнения нативных SQL-запросов
    @PersistenceContext
    private EntityManager entityManager;

    // Метод для получения всех инструментов
    public List<Tool> getAllTools() {
        List<Tool> tools = toolRepository.findAll();
        if (tools.isEmpty()) {
            throw new NoSuchElementException("Инструменты не найдены.");
        }
        return tools;
    }

    // Метод для получения инструментов с пагинацией
    public Page<Tool> getAllToolsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "dateAndTimeAdmission"));
        Page<Tool> tools = toolRepository.findAll(pageable);
        if (tools.isEmpty()) {
            throw new NoSuchElementException("Инструменты не найдены.");
        }
        return tools;
    }

    // Метод для получения инструмента по ID
    public Tool getToolById(String idTool) {
        return toolRepository.findById(idTool)
                .orElseThrow(() -> new NoSuchElementException("Инструмент с ID " + idTool + " не найден."));
    }

    // Метод для получения инструментов по типу
    public List<Tool> getToolsByType(String typeName) {
        TypeOfTool typeOfTool = typeOfToolRepository.findByName(typeName)
                .orElseThrow(() -> new NoSuchElementException("Тип инструмента с названием " + typeName + " не найден."));
        List<Tool> tools = toolRepository.findByTypeOfTool(typeOfTool);
        if (tools.isEmpty()) {
            throw new NoSuchElementException("Инструменты с типом " + typeName + " не найдены.");
        }
        return tools;
    }

    // Метод для получения инструментов по месту хранения
    public List<Tool> getToolsByStorageLocation(String storageName) {
        StorageLocation storageLocation = storageLocationRepository.findByName(storageName)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с названием " + storageName + " не найдено."));
        List<Tool> tools = toolRepository.findByStorageLocation(storageLocation);
        if (tools.isEmpty()) {
            throw new NoSuchElementException("Инструменты в месте хранения " + storageName + " не найдены.");
        }
        return tools;
    }

    // Метод для создания нового инструмента с использованием хранимой процедуры add_tool
    @Transactional
    public void addTool(String articleToolType, String idPlace) {
        typeOfToolRepository.findById(articleToolType)
                .orElseThrow(() -> new NoSuchElementException("Тип инструмента с артикулом " + articleToolType + " не найден."));
        storageLocationRepository.findById(idPlace)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с ID " + idPlace + " не найдено."));
        Query query = entityManager.createNativeQuery("CALL add_tool(:articleToolType, :idPlace)");
        query.setParameter("articleToolType", articleToolType);
        query.setParameter("idPlace", idPlace);
        query.executeUpdate();
    }

    // Метод для массового добавления инструментов с использованием хранимой процедуры add_tool_batch
    @Transactional
    public void addToolBatch(String articleToolType, String idPlace, int toolCount) {
        if (toolCount <= 0) {
            throw new IllegalArgumentException("Количество инструментов должно быть больше нуля.");
        }
        typeOfToolRepository.findById(articleToolType)
                .orElseThrow(() -> new NoSuchElementException("Тип инструмента с артикулом " + articleToolType + " не найден."));
        storageLocationRepository.findById(idPlace)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с ID " + idPlace + " не найдено."));
        Query query = entityManager.createNativeQuery("CALL add_tool_batch(:articleToolType, :idPlace, :toolCount)");
        query.setParameter("articleToolType", articleToolType);
        query.setParameter("idPlace", idPlace);
        query.setParameter("toolCount", toolCount);
        query.executeUpdate();
    }

    // Метод для получения полной информации об инструментах из представления tools_full_info
    public List<Object[]> getToolsFullInfo() {
        Query query = entityManager.createNativeQuery(
                "SELECT id_tool, tool_type_name, storage_name, availability, delivery_date " +
                        "FROM tools_full_info");
        List<Object[]> results = query.getResultList();
        if (results.isEmpty()) {
            throw new NoSuchElementException("Инструменты не найдены.");
        }
        return results;
    }

    // Метод для получения доступных инструментов в месте хранения с использованием хранимой процедуры get_available_tools_in_storage
    public List<Object[]> getAvailableToolsInStorage(String idPlace) {
        storageLocationRepository.findById(idPlace)
                .orElseThrow(() -> new NoSuchElementException("Место хранения с ID " + idPlace + " не найдено."));
        Query query = entityManager.createNativeQuery(
                "CALL get_available_tools_in_storage(:idPlace)");
        query.setParameter("idPlace", idPlace);
        List<Object[]> results = query.getResultList();
        if (results.isEmpty()) {
            throw new NoSuchElementException("Доступные инструменты в месте хранения с ID " + idPlace + " не найдены.");
        }
        return results;
    }

    // Метод для удаления инструмента
    @Transactional
    public void deleteTool(String idTool) {
        Tool tool = toolRepository.findById(idTool)
                .orElseThrow(() -> new NoSuchElementException("Инструмент с ID " + idTool + " не найден."));
        toolRepository.deleteById(idTool);
    }
}