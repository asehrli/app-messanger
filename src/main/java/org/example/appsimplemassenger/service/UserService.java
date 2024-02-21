package org.example.appsimplemassenger.service;

import org.example.appsimplemassenger.controller.UserDto;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.EditUserDto;
import org.example.appsimplemassenger.payload.PasswordDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ApiResponse<UserDto> edit(EditUserDto editUserDto);
    ApiResponse<UserDto> block(UUID userId);
    ApiResponse<UserDto> unblock(UUID userId);
    ApiResponse<Page<User>> all(int page, int size);
    ApiResponse<UserDto> one(UUID id);
    ApiResponse<UserDto> changePassword(PasswordDto passwordDto);
    ApiResponse<List<UserDto>> myBlockedUsers();

}
