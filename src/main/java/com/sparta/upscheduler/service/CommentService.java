package com.sparta.upscheduler.service;
import com.sparta.upscheduler.dto.CommentDTO;
import com.sparta.upscheduler.entity.Comment;
import com.sparta.upscheduler.entity.Schedule;
import com.sparta.upscheduler.entity.User;
import com.sparta.upscheduler.repository.CommentRepository;
import com.sparta.upscheduler.repository.ScheduleRepository;
import com.sparta.upscheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    // 댓글 생성
    public CommentDTO createComment(Long scheduleId, CommentDTO commentDTO) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        User user = (User) userRepository.findByUsername(commentDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setSchedule(schedule);
        comment.setUser(user);
        comment.setContent(commentDTO.getContent());

        comment = commentRepository.save(comment);
        return convertToDTO(comment);
    }

    // 댓글 단건 조회
    public CommentDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return convertToDTO(comment);
    }

    // 댓글 목록 조회
    public List<CommentDTO> getCommentsForSchedule(Long scheduleId) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getSchedule().getId().equals(scheduleId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 댓글 수정
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentDTO.getContent());
        comment.setUpdatedAt(commentDTO.getUpdatedAt());

        comment = commentRepository.save(comment);
        return convertToDTO(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // 엔티티를 DTO로 변환
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUsername(comment.getUser().getUsername());
        dto.setScheduleId(comment.getSchedule().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }
}