package com.sparta.w4_spring_homework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id; // primary key

    @Column(name = "username", length = 50, unique = true)
    private String username; // 아이디

    @JsonIgnore // JSON에 포함 하지 않음
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


//    @OneToMany(mappedBy = "users") //, targetEntity = Post.class
//    private List<Post> post = new ArrayList<>();
//
//    @OneToMany(mappedBy = "users") //, targetEntity = Comment.class
//    private List<Comment> comment = new ArrayList<>();

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
