package com.sparta.w4_spring_homework.repository;

import com.sparta.w4_spring_homework.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByModifiedAtDesc(Long id);
}
