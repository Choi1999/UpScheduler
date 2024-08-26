package com.sparta.upscheduler.service;


import com.sparta.upscheduler.dto.UserDTO;
import com.sparta.upscheduler.entity.User;
import com.sparta.upscheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 유저 생성
    public UserDTO createUser(UserDTO userDTO) {
        validateUserDTO(userDTO);  // 유저 DTO의 필드 값 검사
        User user = convertToEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());  // 생성 시점의 시간 설정
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    // 유저 조회
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    // 유저 전체 조회
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 유저 수정
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        validateUserDTO(userDTO);  // 유저 DTO의 필드 값 검사
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setUpdatedAt(LocalDateTime.now());  // 수정 시점의 시간 설정

        user = userRepository.save(user);
        return convertToDTO(user);
    }

    // 유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 엔티티를 DTO로 변환
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    // DTO를 엔티티로 변환
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        user.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        return user;
    }

    // 유저 DTO 유효성 검사
    private void validateUserDTO(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }
}