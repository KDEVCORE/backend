package com.kdevcore.backend.dto;

import java.time.LocalDateTime;

import com.kdevcore.backend.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private String uuid;
    private String identifier;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public UserDTO(final UserEntity userEntity) {
        this.uuid = userEntity.getUuid();
        this.identifier = userEntity.getIdentifier();
        this.password = userEntity.getPassword();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.createdDate = userEntity.getCreatedDate();
        this.updatedDate = userEntity.getUpdatedDate();
    }

    public static UserEntity toEntity(final UserDTO dto) {
        return UserEntity.builder()
                        .uuid(dto.getUuid())
                        .identifier(dto.getIdentifier())
                        .password(dto.getPassword())
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .build();
    }
}
