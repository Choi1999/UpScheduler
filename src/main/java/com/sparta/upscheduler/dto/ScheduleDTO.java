package com.sparta.upscheduler.dto;
import java.time.LocalDateTime;
import lombok.Data;

import java.time.LocalDateTime;

@Data  // Lombok을 사용하여 getter, setter, toString 등을 자동 생성
public class ScheduleDTO {
    private Long id;
    private String title;
    private String description;
    private String username;  // User 엔티티의 username
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentCount; // 댓글 개수 추가
}