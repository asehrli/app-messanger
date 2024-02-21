package org.example.appsimplemassenger.service;

import org.example.appsimplemassenger.controller.EditMessageDto;
import org.example.appsimplemassenger.entity.Message;
import org.example.appsimplemassenger.payload.AddMessageDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.MessageDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MessageService {
    ApiResponse<MessageDto> add(AddMessageDto addMessageDto);
    ApiResponse<Page<Message>> getAllByChatId(UUID chatId, int page, int size);
    ApiResponse<MessageDto> edit(UUID id, EditMessageDto editMessageDto);
    void delete(UUID id);
}
