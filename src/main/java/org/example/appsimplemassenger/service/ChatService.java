package org.example.appsimplemassenger.service;

import org.example.appsimplemassenger.payload.AddChatDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.ChatDto;

import java.util.UUID;

public interface ChatService {
    ApiResponse<ChatDto> create(AddChatDto addChatDto);
    void delete(UUID id);
}
