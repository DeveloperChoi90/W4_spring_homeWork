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
@Table(name = "POST")
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "POST",cascade = CascadeType.REMOVE)
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