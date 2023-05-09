package com.kdevcore.backend.dto;

import java.time.LocalDateTime;

import com.kdevcore.backend.model.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String uuid;
    private String writer;
    private String headline;
    private String content;
    private Integer views;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public PostDTO(final PostEntity postEntity) {
        this.uuid = postEntity.getUuid();
        this.writer = postEntity.getWriter();
        this.headline = postEntity.getHeadline();
        this.content = postEntity.getContent();
        this.views = postEntity.getViews();
        this.createdDate = postEntity.getCreatedDate();
        this.updatedDate = postEntity.getUpdatedDate();
    }

    public static PostEntity toEntity(final PostDTO postDTO) {
        return PostEntity.builder()
                .uuid(postDTO.getUuid())
                .writer(postDTO.getWriter())
                .headline(postDTO.getHeadline())
                .content(postDTO.getContent())
                .views(postDTO.getViews())
                .build();
    }
}
