package com.sparta.w4_spring_homework.service;

import com.sparta.w4_spring_homework.models.Comment;

import java.util.List;

public interface CommentService {

    void save(Comment comment);
    Comment findById(Long id) throws Exception;
    List<Comment> findAll();
//    void remove(Long id) throws Exception;
}