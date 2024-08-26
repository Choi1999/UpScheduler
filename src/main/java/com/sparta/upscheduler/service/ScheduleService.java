package com.sparta.upscheduler.service;
import com.sparta.upscheduler.dto.ScheduleDTO;
import com.sparta.upscheduler.entity.Schedule;
import com.sparta.upscheduler.entity.User;
import com.sparta.upscheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sparta.upscheduler.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    // 일정 생성
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        User user = (User) userRepository.findByUsername(scheduleDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setDescription(scheduleDTO.getDescription());
        schedule.setUser(user);

        schedule = scheduleRepository.save(schedule);
        return convertToDTO(schedule);
    }

    // 일정 단건 조회
    public ScheduleDTO getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return convertToDTO(schedule);
    }

    // 일정 수정
    public ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setDescription(scheduleDTO.getDescription());
        schedule.setUpdatedAt(scheduleDTO.getUpdatedAt());

        schedule = scheduleRepository.save(schedule);
        return convertToDTO(schedule);
    }

    // 일정 삭제
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    // 엔티티를 DTO로 변환
    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        dto.setUsername(schedule.getUser().getUsername());
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setUpdatedAt(schedule.getUpdatedAt());
        return dto;
    }
}