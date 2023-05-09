package com.kdevcore.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(generator="system-uuid") // ID 자동 생성, 기본 Generator: INCREMENTAL, SEQUENCE, IDENTITY
    @GenericGenerator(name="system-uuid", strategy = "uuid") // Custom Generator 설정
    private String uuid;
    private String writer;
    private String headline;
    private String content;
    private Integer views;
}
