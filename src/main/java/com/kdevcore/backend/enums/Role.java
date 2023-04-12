package com.kdevcore.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_PARTNER("PARTNER"),
    ROLE_MEMBER("MEMBER"),
    ROLE_ADMIN("ADMIN");

    private String desc;
}
