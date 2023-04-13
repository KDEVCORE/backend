package com.kdevcore.backend.dto;

import com.kdevcore.backend.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private String uuid;
    private String userIdentifier;
    private String title;
    private Integer progress;
    private boolean done;
    
    public TodoDTO(final TodoEntity entity) {
        this.uuid = entity.getUuid();
        this.userIdentifier = entity.getUserIdentifier();
        this.title = entity.getTitle();
        this.progress = entity.getProgress();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                        .uuid(dto.getUuid())
                        .userIdentifier(dto.getUserIdentifier())
                        .title(dto.getTitle())
                        .progress(dto.getProgress())
                        .done(dto.isDone())
                        .build();
    }
}
