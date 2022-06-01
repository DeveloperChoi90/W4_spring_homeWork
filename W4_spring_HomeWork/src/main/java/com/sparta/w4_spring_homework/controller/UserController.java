package com.sparta.w4_spring_homework.controller;

import com.sparta.w4_spring_homework.dto.UserInfoDto;
import com.sparta.w4_spring_homework.dto.UserRequestDto;
import com.sparta.w4_spring_homework.models.User;
import com.sparta.w4_spring_homework.models.UserRoleEnum;
import com.sparta.w4_spring_homework.repository.UserRepository;
import com.sparta.w4_spring_homework.security.JwtResponseDto;
import com.sparta.w4_spring_homework.security.UserDetailsImpl;
import com.sparta.w4_spring_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
//    private final JwtTokenProvider jwtTokenProvider;


    // 회원 로그인 페이지
    @PostMapping("/login")
    public JwtResponseDto login(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        if(userDetails.getUsername().equals("anonymousUser")){
            return new JwtResponseDto("이미 로그인이 되어있습니다.");
        }

        try {
            return userService.login(userDetails);
        } catch (Exception e) {
            return new JwtResponseDto("닉네임 또는 패스워드를 확인해주세요");
        }
    }
    //
//    @GetMapping("/user/login/error")
//    public String loginError(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
//        if(userDetails == null){
//            model.addAttribute("user","null");
//        }else{
//
//            model.addAttribute("user",userDetails.getUser().getUsername());
//        }
//        model.addAttribute("loginError", true);
//        return "login";
//    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public UserInfoDto signup(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null){
            return new UserInfoDto(null,null, false);
        }else{
            return new UserInfoDto(userDetails.getUsername(), userDetails.getEmail(), userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN));
        }
    }

    //회원가입 요청처리
    @PostMapping("/user/signup")
    public ResponseEntity<UserRequestDto> registerUser(@Valid @RequestBody UserRequestDto requestDto, BindingResult bindingResult){

        // 회원 ID 중복 확인
        Optional<User> found1 = userRepository.findByUsername(requestDto.getUsername()); // Optional을 쓰면 null을 받을 수 있다.
        if(found1.isPresent()){ // found가 null이 아니면 true를 출력한다.
            FieldError fieldError = new FieldError("requestDto", "username", "이미 존재하는 ID입니다.");
            bindingResult.addError(fieldError);
        }

        if(!requestDto.getPassword().equals(requestDto.getCheckpw())){
            FieldError fieldError = new FieldError("requestDto","checkpw","암호가 일치하지 않습니다.");
            bindingResult.addError(fieldError);
        }

        if (requestDto.getPassword().contains(requestDto.getUsername())) { // indexof가 -1이면 안에 포함이 안되어있dma
            FieldError fieldError = new FieldError("requestDto", "password", "비밀번호에 닉네임과 같은 값을 넣을 수 없습니다.");
            bindingResult.addError(fieldError);
        }

        // 회원 email 중복 확인
        Optional<User> found2 = userRepository.findByEmail(requestDto.getEmail());
        if(found2.isPresent()){ // found가 null이 아니면 true , true이면 같은이메일이 존재한다는 뜻
            FieldError fieldError = new FieldError("requestDto", "email", "이미 존재하는 email입니다.");
            bindingResult.addError(fieldError);
        }
        UserRequestDto user = userService.registerUser(requestDto);
        return ResponseEntity.ok(user);
    }
}