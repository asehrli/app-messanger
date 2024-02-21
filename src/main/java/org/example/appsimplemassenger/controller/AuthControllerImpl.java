package org.example.appsimplemassenger.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.payload.*;
import org.example.appsimplemassenger.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    @Override
    public HttpEntity<ApiResponse<String>> register(RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerDto));
    }
    @Override
    public HttpEntity<ApiResponse<TokenDto>> confirmSms(ConfirmSmsDto confirmSmsDto) {
        return ResponseEntity.accepted().body(authService.confirmSms(confirmSmsDto));
    }
    @Override
    public HttpEntity<ApiResponse<TokenDto>> login(LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
