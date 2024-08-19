package com.yanren.websocketexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    String singUp(@RequestBody SingUpRq dto){
        if (userDetailsManager.userExists(dto.username())){
            return dto.username() + "已存在";
        }
        UserDetails newUser = User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .roles("USER")
                .build();
        userDetailsManager.createUser(newUser);
        return "成功建立帳號:" + dto.username();
    }

    @GetMapping("csrf")
    CsrfToken csrfToken(CsrfToken token) {
        return token;
    }

}

record SingUpRq(
        String username,
        String password
){}
