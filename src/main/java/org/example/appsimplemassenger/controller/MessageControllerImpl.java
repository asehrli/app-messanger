package org.example.appsimplemassenger.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.entity.Message;
import org.example.appsimplemassenger.payload.AddMessageDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.MessageDto;
import org.example.appsimplemassenger.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {
    private final MessageService messageService;
    @Override
    public HttpEntity<ApiResponse<MessageDto>> add(AddMessageDto addMessageDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.add(addMessageDto));
    }

    @Override
    public HttpEntity<ApiResponse<Page<Message>>> getAllByChatId(UUID chatId, int page, int size) {
        return ResponseEntity.ok(messageService.getAllByChatId(chatId, page, size));
    }

    @Override
    public HttpEntity<ApiResponse<MessageDto>> edit(UUID id, EditMessageDto editMessageDto) {
        return ResponseEntity.accepted().body(messageService.edit(id, editMessageDto));
    }

    @Override
    public HttpEntity<ApiResponse<Object>> delete(UUID id) {
        messageService.delete(id);
        return ResponseEntity.notFound().build();
    }
}
