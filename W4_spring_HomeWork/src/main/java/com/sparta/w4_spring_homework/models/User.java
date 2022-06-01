package com.sparta.w4_spring_homework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Table(name = "USERS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id; // primary key

    @Column(name = "USERNAME", length = 50, unique = true)
    private String username; // 아이디

    @JsonIgnore // JSON에 포함 하지 않음
    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    @OneToMany(mappedBy = "USER_ID")
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "USER_ID")
    private List<Comment> comment = new ArrayList<>();

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
