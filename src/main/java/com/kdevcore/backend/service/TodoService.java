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
        log.info("Entity Id: {} is saved.", entity.getUserIdentifier());
        return todoRepository.findByUserIdentifier(entity.getUserIdentifier());
    }

    public List<TodoEntity> retrieve(final String userIdentifier) {
        log.info("Todo retrieve");
        return todoRepository.findByUserIdentifier(userIdentifier);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);
        final Optional<TodoEntity> original = todoRepository.findByUuid(entity.getUuid());
        if(original.isPresent()) {
            todoRepository.save(TodoEntity.builder()
                                    .uuid(entity.getUuid())
                                    .userIdentifier(entity.getUserIdentifier())
                                    .title(entity.getTitle())
                                    .progress(entity.getProgress())
                                    .done(entity.isDone())
                                    .build()); // 데이터베이스 반영
        }
        return retrieve(entity.getUserIdentifier());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try {
            todoRepository.delete(entity);
        } catch(Exception e) {
            String msg = "error deleting entity: " + entity.getUuid();
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
        return retrieve(entity.getUserIdentifier());
    }

    private void validate(final TodoEntity entity) {
        String msg = "";
        if(entity == null) {
            msg = "Entity cannot be null.";
            log.warn(msg);
            throw new RuntimeException(msg);
        }
        if(entity.getUserIdentifier() == null) {
            msg = "Unknown user.";
            log.warn(msg);
            throw new RuntimeException(msg);
        }
    }
}
