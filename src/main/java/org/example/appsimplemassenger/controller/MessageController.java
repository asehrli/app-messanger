package org.example.appsimplemassenger.controller;

import org.example.appsimplemassenger.entity.Message;
import org.example.appsimplemassenger.payload.AddMessageDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/message")
public interface MessageController {
    @PostMapping
    HttpEntity<ApiResponse<MessageDto>> add(@RequestBody AddMessageDto addMessageDto);

    @GetMapping("/by-chat-id/{chatId}")
    HttpEntity<ApiResponse<Page<Message>>> getAllByChatId(@PathVariable UUID chatId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size);
    @PutMapping("/{id}")
    HttpEntity<ApiResponse<MessageDto>> edit(@PathVariable UUID id,
                                             @RequestBody EditMessageDto editMessageDto);

    @DeleteMapping("/{id}")
    HttpEntity<ApiResponse<Object>> delete(@PathVariable UUID id);
}
