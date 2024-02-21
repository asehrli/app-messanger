package org.example.appsimplemassenger.controller;

import jakarta.validation.Valid;
import org.example.appsimplemassenger.payload.*;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthController {
    @PostMapping("/register")
    HttpEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterDto registerDto);
    @PutMapping("/confirm-sms")
    HttpEntity<ApiResponse<TokenDto>> confirmSms(ConfirmSmsDto confirmSmsDto);
    @PostMapping("/login")
    HttpEntity<ApiResponse<TokenDto>> login(@Valid @RequestBody LoginDto loginDto);
}
