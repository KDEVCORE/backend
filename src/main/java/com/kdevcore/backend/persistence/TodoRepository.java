package com.kdevcore.backend.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdevcore.backend.model.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserIdentifier(String userIdentifier);
    Optional<TodoEntity> findByUuid(String uuid);
}
