package com.loginprac.logintest.security.filter;


import com.loginprac.logintest.security.provider.JwtTokenProvider;
import com.loginprac.logintest.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* Authorization모든 요청의 헤더에서 JWT 인증 토큰을 읽음
* 토큰의 유효성을 검사
* 해당 토큰과 관련된사용자 세부 정보를 로드
* Spring Security의 SecurityContextSpring Security는 사용자 세부 정보를 사용하여 권한 부여 검사를 수행
* 컨트롤러에 저장된 사용자 세부 정보에 액세스하여 SecurityContext 비즈니스 로직을 수행
*
* but
* 데이터베이스에서 사용자의 현재 세부 정보를 로드하는 것은 여전히 도움이 안 될 수 있습니다.
* 예를 들어, 사용자의 역할이 변경되었거나 이 JWT를 만든 후 사용자가 자신의 비밀번호를 업데이트한 경우 이 JWT를 사용한 로그인을 허용하지 않을 수 있습니다.
* */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = userDetailsServiceImpl.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}

/*
 * 위의 경우 먼저 요청 헤더 filter에서 검색된 JWT를 구문 분석하고 사용자 ID를 가져옵니다.
 * Authorization그런 다음 데이터베이스에서 사용자 세부 정보를 로드하고 스프링 보안 컨텍스트 내에서 인증을 설정합니다.
 * 위의 데이터베이스 적중 filter은 선택 사항입니다.
 * JWT 클레임 내에서 사용자의 사용자 이름과 역할을 인코딩하고
 * UserDetailsJWT에서 해당 클레임을 구문 분석하여 객체를 생성할 수도 있습니다.
 * 그러면 데이터베이스 히트를 피할 수 있습니다.
 * 그러나 데이터베이스에서 사용자의 현재 세부 정보를 로드하는 것은 여전히 ​​도움이 될 수 있습니다.
 * 예를 들어, 사용자의 역할이 변경되었거나 이 JWT를 만든 후 사용자가 자신의 비밀번호를 업데이트한 경우
 * 이 JWT를 사용한 로그인을 허용하지 않을 수 있습니다.
 */
