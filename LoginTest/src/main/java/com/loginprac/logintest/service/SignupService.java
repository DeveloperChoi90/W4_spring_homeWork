package com.loginprac.logintest.service;


import com.loginprac.logintest.dto.SignupRequestDto;
import com.loginprac.logintest.exception.AppException;
import com.loginprac.logintest.model.Role;
import com.loginprac.logintest.model.RoleName;
import com.loginprac.logintest.model.User;
import com.loginprac.logintest.repository.RoleRepository;
import com.loginprac.logintest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignupService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User signupUser(SignupRequestDto signupRequestDto){
            Role roleUser = new Role();
            roleUser.setName(RoleName.valueOf("ROLE_USER"));
            roleRepository.save(roleUser);
            Role roleAdmin = new Role();
            roleAdmin.setName(RoleName.valueOf("ROLE_ADMIN"));
            roleRepository.save(roleAdmin);

        // Creating user's account
        User user = new User(signupRequestDto.getUsername(), signupRequestDto.getEmail(), signupRequestDto.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        return result;
    }


}
