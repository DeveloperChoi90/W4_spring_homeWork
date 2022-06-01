package com.sparta.w4_spring_homework.dto;

import com.sparta.w4_spring_homework.models.UserRoleEnum;
import com.sparta.w4_spring_homework.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp="^[a-zA-Z0-9]{3,12}$", message="아이디를 3~12자로 입력해주세요.(특수문자x)")
    private String username;

    @Pattern(regexp="^[a-zA-Z0-9]{4,12}$", message="비밀번호를 4~12자로 입력해주세요.(특수문자x)")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String checkpw;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;

    private UserRoleEnum isAdmin;

    private String adminToken = "";

    @Builder
    public UserRequestDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.isAdmin = user.getRole();
    }
}