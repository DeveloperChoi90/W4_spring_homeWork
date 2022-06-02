package com.loginprac.logintest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

//=================================================
/*
 * API가 사용할 요청 및 응답 페이로드 (DTO)
 * 회원 가입 시 DTO
 * */
//=================================================

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Length(min = 3, message = "닉네임은 최소 3자 이상이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "유효하지 않은 닉네임입니다.")
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Length(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String checkPassword;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String role;
}
