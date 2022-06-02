package com.loginprac.logintest.config;

import com.loginprac.logintest.security.JwtAuthenticationEntryPoint;
import com.loginprac.logintest.security.filter.JwtAuthenticationFilter;
import com.loginprac.logintest.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    //Field authenticationManager in service.SecurityServiceImpl required a bean of type 'org.springframework.security.authentication.AuthenticationManager'
    //이 오류나서 추가
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
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
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                //=============================================================
                // 예외를 핸들링 할 떄 사용할 클래스들을 추가해 준다.
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                //===================================================
                // 세션을 사용하지 않기 때문에 STATELESS 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //===================================================
                // authorizeRequests: HttpServletRequest 를 사용하는 요청들에 대한 접근제한 설정
                // permitAll(): 인증(로그인)을 받지 않아도 접근 가능하도록 설정
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability").permitAll()
                .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**").permitAll()

                // TODO: 2022-06-02 테스트를 위해서 임시적으로 모든 API 권한을 풀어둠. ( 삭제 예정 )
                .antMatchers("/api/**").permitAll()
                //===================================================

                //===================================================
                // anyRequest(): 이외의 모든 요청
                // authenticated(): 인증(로그인)을 받아야 접근할 수 있도록 설정
                .anyRequest()
                .authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}