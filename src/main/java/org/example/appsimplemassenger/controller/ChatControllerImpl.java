package org.example.appsimplemassenger.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.payload.AddChatDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.ChatDto;
import org.example.appsimplemassenger.service.ChatService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatControllerImpl implements ChatController {

    private final ChatService chatService;

    @Override
    public HttpEntity<ApiResponse<ChatDto>> create(AddChatDto addChatDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.create(addChatDto));
    }

    @Override
    public HttpEntity<ApiResponse<?>> delete(UUID id) {
        chatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
