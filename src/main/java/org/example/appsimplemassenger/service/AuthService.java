package org.example.appsimplemassenger.service;

import org.example.appsimplemassenger.payload.*;

public interface AuthService {
    ApiResponse<String> register(RegisterDto registerDto);
    ApiResponse<TokenDto> login(LoginDto loginDto);
    ApiResponse<TokenDto> confirmSms(ConfirmSmsDto confirmSmsDto);
}
