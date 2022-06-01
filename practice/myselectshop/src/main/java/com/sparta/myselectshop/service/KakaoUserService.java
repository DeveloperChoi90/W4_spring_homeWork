package com.sparta.myselectshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.myselectshop.dto.KakaoUserInfoDto;
import com.sparta.myselectshop.model.User;
import com.sparta.myselectshop.util.KakaoLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KakaoUserService {
    private final KakaoLogin kakaoLogin;

    public void kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = kakaoLogin.getAcessToken(code);
        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = kakaoLogin.getKakoUserInfo(accessToken);
        // 3. 회원가입
        User kakaoUser  = kakaoLogin.signupKakaoUserToUser(kakaoUserInfo);
        // 4. 강제 로그인 처리
        kakaoLogin.forceLogin(kakaoUser);
    }
}