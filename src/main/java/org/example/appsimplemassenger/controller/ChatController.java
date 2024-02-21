package org.example.appsimplemassenger.controller;

import jakarta.validation.Valid;
import org.example.appsimplemassenger.payload.AddChatDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.ChatDto;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/chat")
public interface ChatController {
    @PostMapping
    HttpEntity<ApiResponse<ChatDto>> create(@Valid @RequestBody AddChatDto addChatDto);
    @DeleteMapping("/{id}")
    HttpEntity<ApiResponse<?>> delete(@PathVariable UUID id);
}
