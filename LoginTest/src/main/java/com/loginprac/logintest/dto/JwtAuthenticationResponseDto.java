package com.loginprac.logintest.dto;

import lombok.Getter;
import lombok.Setter;

//=================================================
/*
 * API가 사용할 요청 및 응답 페이로드 (DTO)
 * JWT인증 응답 DTO
 * */
//=================================================

@Getter
@Setter
public class JwtAuthenticationResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
