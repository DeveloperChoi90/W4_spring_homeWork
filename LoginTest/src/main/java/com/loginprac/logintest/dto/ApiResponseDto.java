package com.loginprac.logintest.dto;

import lombok.Getter;
import lombok.Setter;

//=================================================
/*
 * API가 사용할 요청 및 응답 페이로드 (DTO)
 * API 응답 DTO
 * */
//=================================================

@Getter
@Setter
public class ApiResponseDto {
    private Boolean success;
    private String message;

    public ApiResponseDto(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
