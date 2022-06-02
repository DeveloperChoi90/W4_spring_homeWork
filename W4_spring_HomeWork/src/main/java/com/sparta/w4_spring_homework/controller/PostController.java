package com.sparta.w4_spring_homework.controller;

import com.sparta.w4_spring_homework.dto.PostRequestDto;
import com.sparta.w4_spring_homework.models.Comment;
import com.sparta.w4_spring_homework.models.Post;
import com.sparta.w4_spring_homework.repository.CommentRepository;
import com.sparta.w4_spring_homework.repository.PostRepository;
import com.sparta.w4_spring_homework.repository.UserRepository;
import com.sparta.w4_spring_homework.security.UserDetailsImpl;
import com.sparta.w4_spring_homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    // 게시글 조회
    @GetMapping("/posts")
    public List<Post> getPosts(){
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    // 게시글 작성
    @PostMapping("/posts")
    public String createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto){
        requestDto.setUser(userDetails.getUser());
        return postService.savePost(requestDto);
    }

    //게시글 상세 조회
    @GetMapping("/posts/{id}")
    public Post getOneBoard(@PathVariable Long id){

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        List<Comment> comment = commentRepository.findByPostIdOrderByModifiedAtDesc(id);
        post.setComment(comment);
        return post;
    }

    // 게시글 수정
/*    @PutMapping("/api/posts/{id}/edit")
    public String updateBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        PostRequestDto postRequestDto = new PostRequestDto();
         = userRepository.findByUsername(userDetails.getUsername());

        }
        postRequestDto.setUser(userDetails.getUser());
        postService.update(id, postRequestDto);

        return "redirect:/";

    }*/

/*    @DeleteMapping("/api/posts/{id}")
    public String deleteBoard(@PathVariable Long id){
        postRepository.deleteById(id);
        return "redirect:/";
    }*/
}
