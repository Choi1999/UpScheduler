package com.sparta.upscheduler.dto;

public class ScheduleUserDTO {
    private Long scheduleId; // schedule_id
    private Long userId; // user_id

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}