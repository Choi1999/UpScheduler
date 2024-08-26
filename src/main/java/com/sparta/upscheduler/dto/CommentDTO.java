package com.sparta.upscheduler.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data  // Lombok을 사용하여 getter, setter, toString 등을 자동 생성
public class CommentDTO {
    private Long id;
    private String content;
    private String username;  // User 엔티티의 username
    private Long scheduleId;   // Schedule 엔티티의 ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}