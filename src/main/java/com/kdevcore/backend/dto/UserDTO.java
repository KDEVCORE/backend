package com.kdevcore.backend.dto;

import java.time.LocalDateTime;

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
}
