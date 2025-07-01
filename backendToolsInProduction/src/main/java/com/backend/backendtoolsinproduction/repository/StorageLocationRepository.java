package com.backend.backendtoolsinproduction.repository;

import com.backend.backendtoolsinproduction.model.StorageLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Репозиторий для работы с местами хранения, предоставляет методы для поиска и управления местами хранения
@Repository
public interface StorageLocationRepository extends JpaRepository<StorageLocation, String> {

    // Поиск места хранения по названию
    Optional<StorageLocation> findByName(String name);
}
