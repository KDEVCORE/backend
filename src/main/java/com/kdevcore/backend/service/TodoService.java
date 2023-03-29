package com.kdevcore.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdevcore.backend.model.TodoEntity;
import com.kdevcore.backend.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    
    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);
        todoRepository.save(entity);
        log.info("Entity Id: {} is saved.", entity.getId());
        return todoRepository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        log.info("Todo retrieve");
        return todoRepository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);
        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
        original.ifPresent((todo) -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todoRepository.save(todo); // 데이터베이스 반영
        });
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try {
            todoRepository.delete(entity);
        } catch(Exception e) {
            String msg = "error deleting entity: " + entity.getId();
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
        return retrieve(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        String msg = "";
        if(entity == null) {
            msg = "Entity cannot be null.";
            log.warn(msg);
            throw new RuntimeException(msg);
        }
        if(entity.getUserId() == null) {
            msg = "Unknown user.";
            log.warn(msg);
            throw new RuntimeException(msg);
        }
    }
}
