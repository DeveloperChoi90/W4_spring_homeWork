package com.sparta.w4_spring_homework.service;

import com.sparta.w4_spring_homework.dto.PostRequestDto;
import com.sparta.w4_spring_homework.models.Post;
import com.sparta.w4_spring_homework.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public String savePost(PostRequestDto postRequestDto){
        if(postRequestDto.getText().equals("")) return "게시물을 입력해 주세요.";
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return "게시물이 저장되었습니다.";
    }

    @Transactional
    public Long update(Long id, PostRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        post.update(requestDto);
        return post.getId();
    }
}
