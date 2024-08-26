package com.sparta.upscheduler.service;
import com.sparta.upscheduler.dto.ScheduleDTO;
import com.sparta.upscheduler.dto.ScheduleOneDTO;
import com.sparta.upscheduler.dto.UserDTO;
import com.sparta.upscheduler.entity.Schedule;
import com.sparta.upscheduler.entity.User;
import com.sparta.upscheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sparta.upscheduler.repository.ScheduleRepository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    // 일정 페이징 조회
    public Page<ScheduleDTO> getSchedules(int page, int size) {
        // 페이지 크기가 제공되지 않으면 기본값을 10으로 설정
        size = (size == 0) ? 10 : size;

        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        // Page<Schedule>을 Page<ScheduleDTO>로 변환
        return schedules.map(this::convertToDTO);
    }

    // 일정 단건 조회
    public ScheduleOneDTO getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // 지연 로딩된 엔티티를 필요할 때만 초기화합니다.
        schedule.getAssignedUsers().size();  // 필요한 경우 지연 로딩 초기화

        return convertToDetailDTO(schedule);
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

    // DTO 변환 메서드들
    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        dto.setUsername(schedule.getUser().getUsername());
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setUpdatedAt(schedule.getUpdatedAt());
        dto.setCommentCount(schedule.getComments().size());
        return dto;
    }

    private ScheduleOneDTO convertToDetailDTO(Schedule schedule) {
        ScheduleOneDTO dto = new ScheduleOneDTO();
        dto.setId(schedule.getId());
        dto.setTitle(schedule.getTitle());
        dto.setDescription(schedule.getDescription());
        dto.setUsername(schedule.getUser().getUsername());
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setUpdatedAt(schedule.getUpdatedAt());
        dto.setCommentCount(schedule.getComments().size());
        dto.setAssignedUsers(schedule.getAssignedUsers().stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(user.getId());
                    userDTO.setUsername(user.getUsername());
                    userDTO.setEmail(user.getEmail());
                    return userDTO;
                })
                .collect(Collectors.toList()));
        return dto;
    }
}