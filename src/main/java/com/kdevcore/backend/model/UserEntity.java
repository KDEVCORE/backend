package com.kdevcore.backend.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.kdevcore.backend.enums.Provider;
import com.kdevcore.backend.enums.Role;
import com.kdevcore.backend.oauth2.OAuth2UserInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uuid;
    private String identifier;
    private String password;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity update(OAuth2UserInfo oAuth2UserInfo) {
        this.identifier = oAuth2UserInfo.getIdentifier();
        this.name = oAuth2UserInfo.getName();
        this.email = oAuth2UserInfo.getEmail();
        return this;
    }
}
