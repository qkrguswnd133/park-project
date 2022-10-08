package com.spring.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_jpa_file")
@NoArgsConstructor
@Data
public class BoardFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(nullable = false)
    private String originalFillName;

    @Column(nullable = false)
    private String storedFilePath;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String creatorId;

    @Column(nullable = false)
    private LocalDateTime createdDatetime = LocalDateTime.now();

    private String updatorId;

    private LocalDateTime updateDateTime;
}
