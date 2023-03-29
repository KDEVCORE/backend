package com.kdevcore.backend.dto;

import com.kdevcore.backend.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private String id;
    private String title;
    private Integer progress;
    private boolean done;

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.progress = entity.getProgress();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                        .id(dto.getId())
                        .title(dto.getTitle())
                        .progress(dto.getProgress())
                        .done(dto.isDone())
                        .build();
    }
}
