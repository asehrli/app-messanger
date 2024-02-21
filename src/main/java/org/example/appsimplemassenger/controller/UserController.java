package org.example.appsimplemassenger.controller;

import jakarta.validation.Valid;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.EditUserDto;
import org.example.appsimplemassenger.payload.PasswordDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/user")
public interface UserController {
    @PatchMapping("/change-password")
    HttpEntity<ApiResponse<UserDto>> changePassword(PasswordDto passwordDto);

    @PutMapping
    HttpEntity<ApiResponse<UserDto>> edit(@Valid @RequestBody EditUserDto editUserDto);

    @PatchMapping("/block/{userId}")
    HttpEntity<ApiResponse<UserDto>> block(@PathVariable UUID userId);

    @PatchMapping("/un-block/{userId}")
    HttpEntity<ApiResponse<UserDto>> unblock(@PathVariable UUID userId);

    @GetMapping
    HttpEntity<ApiResponse<Page<User>>> all(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    HttpEntity<ApiResponse<UserDto>> one(@PathVariable UUID id);

    @GetMapping("/my-blocked-users")
    HttpEntity<ApiResponse<List<UserDto>>> myBlockedUsers();

}
