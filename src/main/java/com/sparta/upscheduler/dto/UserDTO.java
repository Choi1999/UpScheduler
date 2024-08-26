package com.sparta.upscheduler.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data  // Lombok을 사용하여 getter, setter, toString 등을 자동 생성
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;  // User 생성 시각
    private LocalDateTime updatedAt;  // User 수정 시각
}