package com.kdevcore.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdevcore.backend.dto.ResponseDTO;
import com.kdevcore.backend.dto.TodoDTO;
import com.kdevcore.backend.model.TodoEntity;
import com.kdevcore.backend.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Todo")
@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;
    
    @Operation(summary = "Todo item registration process handling", description = "할 일 목록 추가 처리")
    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userIdentifier, @RequestBody TodoDTO dto) {
        try {
            dto.setUserIdentifier(userIdentifier);
            TodoEntity todo = TodoDTO.toEntity(dto);
            List<TodoEntity> entities = todoService.create(todo);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "Todo item list get process handling", description = "할 일 목록 조회 처리")
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userIdentifier) {
        List<TodoEntity> entities = todoService.retrieve(userIdentifier);
        log.info("Reading data(entity): " + entities.toString());
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        log.info("Reading data(dto): " + dtos.toString());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Todo item modification process handling", description = "할 일 목록 수정 처리")
    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userIdentifier, @RequestBody TodoDTO dto) {
        dto.setUserIdentifier(userIdentifier);
        TodoEntity entity = TodoDTO.toEntity(dto);
        List<TodoEntity> entities = todoService.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
    
    @Operation(summary = "Todo item removal process handling", description = "할 일 목록 삭제 처리")
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userIdentifier, @RequestBody TodoDTO dto) {
        try {
            dto.setUserIdentifier(userIdentifier);
            TodoEntity entity = TodoDTO.toEntity(dto);
            List<TodoEntity> entities = todoService.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
