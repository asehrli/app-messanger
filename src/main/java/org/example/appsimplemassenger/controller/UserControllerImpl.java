package org.example.appsimplemassenger.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.EditUserDto;
import org.example.appsimplemassenger.payload.PasswordDto;
import org.example.appsimplemassenger.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public HttpEntity<ApiResponse<UserDto>> changePassword(PasswordDto passwordDto) {
        return ResponseEntity.accepted().body(userService.changePassword(passwordDto));
    }

    @Override
    public HttpEntity<ApiResponse<UserDto>> edit(EditUserDto editUserDto) {
        return ResponseEntity.accepted().body(userService.edit(editUserDto));
    }
    @Override
    public HttpEntity<ApiResponse<UserDto>> block(UUID userId) {
        return ResponseEntity.accepted().body(userService.block(userId));
    }
    @Override
    public HttpEntity<ApiResponse<UserDto>> unblock(UUID userId) {
        return ResponseEntity.accepted().body(userService.unblock(userId));
    }

    @Override
    public HttpEntity<ApiResponse<Page<User>>> all(int page, int size) {
        return ResponseEntity.ok(userService.all(page, size));
    }

    @Override
    public HttpEntity<ApiResponse<List<UserDto>>> myBlockedUsers() {
        return ResponseEntity.ok(userService.myBlockedUsers());
    }

    @Override
    public HttpEntity<ApiResponse<UserDto>> one(UUID id) {
        return ResponseEntity.ok(userService.one(id));
    }
}
