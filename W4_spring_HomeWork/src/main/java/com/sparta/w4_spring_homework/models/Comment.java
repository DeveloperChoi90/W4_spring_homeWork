package com.sparta.w4_spring_homework.models;

import com.sparta.w4_spring_homework.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") //name = "POST_ID"
    private Post post;

    public Comment(CommentRequestDto requestDto) {
        this.text = requestDto.getText();
        this.user = requestDto.getUser();
        this.post = requestDto.getPost();
    }

    public void modify(String text) {
        this.text = text;
    }
}