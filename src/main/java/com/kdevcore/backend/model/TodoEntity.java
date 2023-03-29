package com.kdevcore.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Todo") // name 규정, @Table 없으면 @Entity, 없으면 class name
public class TodoEntity {
    @Id
    @GeneratedValue(generator="system-uuid") // ID 자동 생성, 기본 Generator: INCREMENTAL, SEQUENCE, IDENTITY
    @GenericGenerator(name="system-uuid", strategy = "uuid") // Custom Generator 설정
    private String id;
    private String userId;
    private String title;
    private Integer progress;
    private boolean done;
}
