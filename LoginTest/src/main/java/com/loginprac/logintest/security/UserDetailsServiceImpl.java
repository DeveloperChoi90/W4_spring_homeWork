package com.loginprac.logintest.security;

import com.loginprac.logintest.model.User;
import com.loginprac.logintest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//==================================================================================================
/**
 * 첫 번째 방법 loadUserByUsername()은 Spring 보안에서 사용됩니다.메소드 의 사용에 주의하십시오
 * findByUsernameOrEmail. 이를 통해 사용자는 사용자 이름이나 이메일을 사용하여 로그인할 수 있습니다.
 */
//==================================================================================================
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)  // loadUserByUsername() 메서드는 UserDetails Spring Security가 다양한 인증 및 역할 기반 유효성 검사를 수행하는 데사용하는객체를 반환
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserDetailsImpl.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserDetailsImpl.create(user);
    }

}