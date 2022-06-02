package com.loginprac.logintest.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;


/**
 * Spring 보안은 @AuthenticationPrincipal컨트롤러에서
 * 현재 인증된 사용자에 액세스하기 위해 호출되는 주석을 제공합니다.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}


/**
 * 우리 프로젝트의 모든 곳에서 Spring Security
 * 관련 주석에 너무 많이 얽매이지 않도록 메타 주석을 만들었습니다.
 * 이것은 Spring Security에 대한 의존성을 감소시킨다.
 * CurrentUser따라서 프로젝트에서 Spring Security를 제거하기로 결정했다면
 * 주석 을 변경하기만 하면 쉽게 제거할 수 있습니다.
 */