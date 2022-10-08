package com.spring.project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {
    private int boardIdx;
    private String title;
    private String content;
    private int hitCnt;
    private String creatorId;
    private LocalDateTime createDatetime;
    private String updaterId;
    private LocalDateTime updateDatetime;
}
