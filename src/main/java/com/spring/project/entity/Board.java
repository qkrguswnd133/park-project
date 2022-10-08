package com.spring.project.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity @Data
@Table(name = "t_jpa_board")
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardIdx;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String content;

    @Column(nullable = false)
    private int hitCnt = 0;

    @Column(nullable = false)
    private String creatorId;

    @Column(nullable = true, updatable = false)
    private LocalDateTime createdDatetime = LocalDateTime.now();

    private String updaterId;

    private LocalDateTime updateDatetime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_Idx")
    private Collection<BoardFileEntity> fileList;

}
