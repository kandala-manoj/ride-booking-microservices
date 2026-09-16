package com.alpha.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.authservice.dto.LoginDto;
import com.alpha.authservice.dto.RegisterDto;
import com.alpha.authservice.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public String register(@RequestBody RegisterDto registerDto) {

        return authService.register(
                registerDto.getUsername(),
                registerDto.getPassword()
        );
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody LoginDto loginDto) {

        return authService.login(
                loginDto.getUsername(),
                loginDto.getPassword()
        );
    }
}