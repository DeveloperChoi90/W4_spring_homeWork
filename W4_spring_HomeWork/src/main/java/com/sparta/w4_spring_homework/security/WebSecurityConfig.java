package com.sparta.w4_spring_homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Override
    public void configure(WebSecurity web) {
        //===================================================
        // ignoring(): 여기서 설정한 요청들은 스프링 시큐리티 로직을 수행하지 않음
        // Security filter chain 을 적용할 필요가 없을 때 사용한다. -> 알아보기
        web.ignoring()
                .antMatchers("/h2-console/**"
                        , "/favicon.ico"
                        , "/error"
                );
        //===================================================
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable();
        http.csrf().disable();
        //===================================================
        // 세션을 사용하지 않기 때문에 STATELESS 로 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
        http.formLogin().disable();

        // 그 외 모든 요청은 인증과정 필요
        http.authorizeRequests()
                //================================================================================
                // authorizeRequests: HttpServletRequest 를 사용하는 요청들에 대한 접근제한 설정
                // permitAll(): 인증(로그인)을 받지 않아도 접근 가능하도록 설정
                .antMatchers("/user/**").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/api/signup").permitAll() // 회원가입 API 요청 허용
                .antMatchers("/api/posts").permitAll()

                // TODO: 2022-06-02 테스트를 위해서 임시적으로 모든 API 권한을 풀어둠. ( 삭제 예정 )
                .antMatchers("/api/**").permitAll()

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
