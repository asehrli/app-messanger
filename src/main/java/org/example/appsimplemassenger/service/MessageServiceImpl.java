package org.example.appsimplemassenger.service;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.controller.EditMessageDto;
import org.example.appsimplemassenger.entity.Attachment;
import org.example.appsimplemassenger.entity.Chat;
import org.example.appsimplemassenger.entity.Message;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.exception.MyBadRequestException;
import org.example.appsimplemassenger.exception.MyNotFoundException;
import org.example.appsimplemassenger.mapper.MessageMapper;
import org.example.appsimplemassenger.payload.AddMessageDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.MessageDto;
import org.example.appsimplemassenger.repository.ChatRepository;
import org.example.appsimplemassenger.repository.MessageRepository;
import org.example.appsimplemassenger.util.PrincipalUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;

    @Override
    public ApiResponse<MessageDto> add(AddMessageDto addMessageDto) {
        User user = PrincipalUtil.currentUser();

        Chat chat = chatRepository.findById(addMessageDto.chatId()).orElseThrow(() -> new MyNotFoundException("Chat not found!"));
        if (!(chat.getUser().getId().equals(user.getId()) || chat.getReceiver().getId().equals(user.getId())))
            throw new MyNotFoundException("Chat not found for you!");

        if (addMessageDto.text() != null && addMessageDto.file() != null)
            throw new MyBadRequestException("Message content doesn't exist");
        Message message = Message.builder()
                .user(user)
                .chat(chat)
                .text(addMessageDto.text())
                .build();

        if (addMessageDto.file() != null) {
            String basePath = "/src/main/resources";
            LocalDateTime now = LocalDateTime.now();
            String ofn = addMessageDto.file().getOriginalFilename();
            assert ofn != null;
            String globalPath = "/static/%s/%s/%s/%s.%s"
                    .formatted(now.getYear(),
                            now.getMonth(),
                            now.getDayOfMonth(),
                            UUID.randomUUID(),
                            ofn.substring(ofn.lastIndexOf('.' + 1)));

            try {
                Files.copy(addMessageDto.file().getInputStream(), Path.of(basePath.concat(globalPath)), StandardCopyOption.REPLACE_EXISTING);
                message.setAttachment(Attachment.builder()
                        .originalName(addMessageDto.file().getOriginalFilename())
                        .contentType(addMessageDto.file().getContentType())
                        .size(addMessageDto.file().getSize())
                        .contentUrl(globalPath)
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        messageRepository.save(message);

        return ApiResponse.success(messageMapper.toDto(message));
    }

    @Override
    public ApiResponse<Page<Message>> getAllByChatId(UUID chatId, int page, int size) {
        return ApiResponse.success(messageRepository.findByChatId(chatId, PageRequest.of(page, size)));
    }

    @Override
    public ApiResponse<MessageDto> edit(UUID id, EditMessageDto editMessageDto) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new MyNotFoundException("Message not found!"));
        if (!message.getUser().getId().equals(PrincipalUtil.currentUser().getId()))
            throw new MyNotFoundException("Message not found for you!");
        message.setText(editMessageDto.text());
        messageRepository.save(message);
        return ApiResponse.success(messageMapper.toDto(message));
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new MyNotFoundException("Message not found!"));
        if (!message.getUser().getId().equals(PrincipalUtil.currentUser().getId()))
            throw new MyNotFoundException("Message not found for you!");

        messageRepository.delete(message);
    }
}
