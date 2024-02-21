package org.example.appsimplemassenger.service;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.entity.Chat;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.exception.MyBadRequestException;
import org.example.appsimplemassenger.exception.MyConflictException;
import org.example.appsimplemassenger.exception.MyException;
import org.example.appsimplemassenger.exception.MyNotFoundException;
import org.example.appsimplemassenger.mapper.ChatMapper;
import org.example.appsimplemassenger.payload.AddChatDto;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.ChatDto;
import org.example.appsimplemassenger.repository.ChatRepository;
import org.example.appsimplemassenger.repository.UserRepository;
import org.example.appsimplemassenger.util.PrincipalUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Override
    public ApiResponse<ChatDto> create(AddChatDto addChatDto) {
        User user = PrincipalUtil.currentUser();
        User receiver = userRepository
                .findById(addChatDto.receiverId())
                .orElseThrow(() -> new MyNotFoundException("Receiver not found!"));

        if (chatRepository.existsByIdAndReceiverId(user.getId(), receiver.getId())) {
            throw new MyConflictException("Chat already exists!");
        }

        if (chatRepository.existsByIdAndReceiverId(receiver.getId(), user.getId())) {
            throw new MyConflictException("Chat already exists!");
        }

        Chat chat = Chat.builder()
                .user(user)
                .receiver(receiver)
                .build();

        chatRepository.save(chat);

        return ApiResponse.success(chatMapper.toDto(chat));
    }

    @Override
    public void delete(UUID id) {
        User user = PrincipalUtil.currentUser();
        Chat chat = chatRepository
                .findById(id)
                .orElseThrow(() -> new MyNotFoundException("Chat not found by id!"));

        if (!(chat.getUser().getId().equals(user.getId()) || chat.getReceiver().getId().equals(user.getId())))
            throw new MyNotFoundException("Chat not found by id!");

        chatRepository.deleteById(id);
    }
}
