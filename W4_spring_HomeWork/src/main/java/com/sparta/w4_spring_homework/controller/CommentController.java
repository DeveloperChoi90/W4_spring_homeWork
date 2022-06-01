package com.sparta.w4_spring_homework.controller;

import com.sparta.w4_spring_homework.dto.CommentRequestDto;
import com.sparta.w4_spring_homework.models.Post;
import com.sparta.w4_spring_homework.models.Comment;
import com.sparta.w4_spring_homework.repository.CommentRepository;
import com.sparta.w4_spring_homework.repository.PostRepository;
import com.sparta.w4_spring_homework.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/api/posts/{id}/comment")
    public String createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = new Comment(requestDto);
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        comment.setUser(userDetails.getUser());
        comment.setPost(post);
        commentRepository.save(comment);
        return "redirect:/api/posts/{id}";
    }

    @PutMapping("/api/posts/{id}/comment/{commentId}")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        comment.setText(requestDto.getText());

        commentRepository.save(comment);
        return "redirect:/api/posts/{id}";
    }

    @DeleteMapping("/api/posts/{id}/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
        return "redirect:/api/posts/{id}";
    }
}
