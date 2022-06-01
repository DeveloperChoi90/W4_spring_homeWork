package com.sparta.w4_spring_homework.dto;

import com.sparta.w4_spring_homework.models.Post;
import com.sparta.w4_spring_homework.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String text;
    private User user;
    private Post post;
}