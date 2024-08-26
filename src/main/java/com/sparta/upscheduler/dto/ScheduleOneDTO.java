package com.sparta.upscheduler.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleOneDTO {
    private Long id;
    private String title;
    private String description;
    private String username;  // 작성 유저명
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentCount; // 댓글 개수
    private List<UserDTO> assignedUsers; // 담당 유저 정보 포함
}

