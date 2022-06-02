package com.loginprac.logintest.controller;

import com.loginprac.logintest.dto.ApiResponseDto;
import com.loginprac.logintest.exception.AppException;
import com.loginprac.logintest.model.Role;
import com.loginprac.logintest.model.RoleName;
import com.loginprac.logintest.model.User;
import com.loginprac.logintest.dto.JwtAuthenticationResponseDto;
import com.loginprac.logintest.dto.LoginRequestDto;
import com.loginprac.logintest.dto.SignupRequestDto;
import com.loginprac.logintest.repository.RoleRepository;
import com.loginprac.logintest.repository.UserRepository;
import com.loginprac.logintest.security.provider.JwtTokenProvider;
import com.loginprac.logintest.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//=============================================================
// 로그인 및 가입을 위한 API
//=============================================================

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SignupService signupService;

    @Autowired
    JwtTokenProvider tokenProvider;

//    @GetMapping("/test")
//    public String test(){
//        return "test";
//    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponseDto(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signUpRequestDto) {
//        log.error(signUpRequestDto.getCheckPassword());

        if(userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            return new ResponseEntity(new ApiResponseDto(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            return new ResponseEntity(new ApiResponseDto(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequestDto.getUsername(),
                signUpRequestDto.getEmail(), signUpRequestDto.getPassword());

        if(!Objects.equals(signUpRequestDto.getCheckPassword(), user.getPassword())) {
            return new ResponseEntity(new ApiResponseDto(false, "Password is not correct!!"),
                    HttpStatus.BAD_REQUEST);
        }

//        //======================================================
//        // 기본 상태 ROLE_USER
//        Role baseRole = new Role();
//        baseRole.setName(RoleName.ROLE_USER);
//        roleRepository.save(baseRole);

//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                .orElseThrow(() -> new AppException("User Role not set."));
//
//        user.setRoles(Collections.singleton(userRole));

        User result = signupService.signupUser(signUpRequestDto);  //userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto(true, "User registered successfully"));
    }
}
