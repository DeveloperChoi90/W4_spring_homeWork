package com.sparta.w4_spring_homework.security;

import com.sparta.w4_spring_homework.models.User;
import com.sparta.w4_spring_homework.models.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
//    private final Long userId;
//    private final String password;
//    private final String username;
//    private final UserRoleEnum role;
    private final User user;

    public User getUser() {
        return user;
    }
    /*@Builder
    public UserDetailsImpl(Long userId, String username, UserRoleEnum role){
        this.userId = userId;
        this.username = username;
        this.role = role;
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = getUser().getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername(){
        return user.getUsername();
    }

    @Override
    public String getPassword() {return user.getPassword();}

    public String getEmail() {return user.getEmail();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}