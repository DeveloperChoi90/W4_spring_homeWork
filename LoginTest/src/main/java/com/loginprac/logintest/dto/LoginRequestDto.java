package com.loginprac.logintest.dto;

import com.loginprac.logintest.model.RoleName;

import javax.persistence.EnumType;
import javax.validation.constraints.NotBlank;

//=================================================
/*
* API가 사용할 요청 및 응답 페이로드 (DTO)
* Login DTO
* */
//=================================================

public class LoginRequestDto {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}