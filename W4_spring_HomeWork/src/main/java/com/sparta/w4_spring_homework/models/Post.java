package com.sparta.w4_spring_homework.models;

import com.sparta.w4_spring_homework.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false) //name = "USER_ID"
    private User user;

    @OneToMany(mappedBy = "post", targetEntity = Comment.class, cascade = CascadeType.REMOVE)
    List<Comment> comment = new ArrayList<>();


    public Post(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.text = requestDto.getText();
        this.user = requestDto.getUser();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.text = requestDto.getText();
        this.user = requestDto.getUser();
    }
}