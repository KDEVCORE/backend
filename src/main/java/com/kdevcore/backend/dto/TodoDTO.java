package com.kdevcore.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Boolean done;
    private Integer progress;
    private Integer priority;
    private Integer stress;
    private LocalDate deadline;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public TodoDTO(final TodoEntity entity) {
        this.uuid = entity.getUuid();
        this.userIdentifier = entity.getUserIdentifier();
        this.title = entity.getTitle();
        this.done = entity.getDone();
        this.progress = entity.getProgress();
        this.priority = entity.getPriority();
        this.stress = entity.getStress();
        this.deadline = entity.getDeadline();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }

    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .uuid(dto.getUuid())
                .userIdentifier(dto.getUserIdentifier())
                .title(dto.getTitle())
                .done(dto.getDone())
                .progress(dto.getProgress())
                .priority(dto.getPriority())
                .stress(dto.getStress())
                .deadline(dto.getDeadline())
                .build();
    }
}
