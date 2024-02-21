package org.example.appsimplemassenger.service;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.controller.UserDto;
import org.example.appsimplemassenger.entity.Attachment;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.exception.MyBadRequestException;
import org.example.appsimplemassenger.exception.MyConflictException;
import org.example.appsimplemassenger.exception.MyNotFoundException;
import org.example.appsimplemassenger.mapper.UserMapper;
import org.example.appsimplemassenger.payload.ApiResponse;
import org.example.appsimplemassenger.payload.EditUserDto;
import org.example.appsimplemassenger.payload.PasswordDto;
import org.example.appsimplemassenger.repository.UserRepository;
import org.example.appsimplemassenger.util.PrincipalUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ApiResponse<UserDto> edit(EditUserDto editUserDto) {
        User user = PrincipalUtil.currentUser();
        user.setFirstname(editUserDto.firstname());
        user.setLastname(editUserDto.lastname());

        if (editUserDto.photo() != null) {
            String basePath = "/src/main/resources";
            LocalDateTime now = LocalDateTime.now();
            String globalPath = "/static/%s/%s/%s/%s.jpg".formatted(now.getYear(), now.getMonth(), now.getDayOfMonth(), UUID.randomUUID());

            try {
                Files.copy(editUserDto.photo().getInputStream(), Path.of(basePath.concat(globalPath)), StandardCopyOption.REPLACE_EXISTING);
                user.setAttachment(Attachment.builder()
                        .originalName(editUserDto.photo().getOriginalFilename())
                        .contentType(editUserDto.photo().getContentType())
                        .size(editUserDto.photo().getSize())
                        .contentUrl(globalPath)
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        userRepository.save(user);

        return ApiResponse.success(userMapper.toDto(user));
    }

    @Override
    public ApiResponse<UserDto> block(UUID userId) {
        User user = PrincipalUtil.currentUser();
        User blackUser = getUserByIdOrElseThrow(userId);
        if (user.getBlackList().stream().noneMatch(u -> u.getId().equals(blackUser.getId()))) {
            user.getBlackList().add(blackUser);
        } else {
            throw new MyConflictException("User already blocked!");
        }

        userRepository.save(user);

        return ApiResponse.success(userMapper.toDto(user));
    }

    private User getUserByIdOrElseThrow(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new MyNotFoundException("User not found by id!"));
    }

    @Override
    public ApiResponse<UserDto> unblock(UUID userId) {
        User user = PrincipalUtil.currentUser();
        User blackUser = getUserByIdOrElseThrow(userId);

        if (user.getBlackList().stream().noneMatch(u -> u.getId().equals(blackUser.getId()))) {
            throw new MyNotFoundException("User not found for unblocking!");
        } else {
            user.getBlackList().removeIf(u -> u.getId().equals(blackUser.getId()));
        }

        userRepository.save(user);
        return ApiResponse.success(userMapper.toDto(user));
    }

    @Override
    public ApiResponse<Page<User>> all(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return ApiResponse.success(users);
    }

    @Override
    public ApiResponse<UserDto> one(UUID id) {
        return ApiResponse.success(userMapper.toDto(getUserByIdOrElseThrow(id)));
    }

    @Override
    public ApiResponse<UserDto> changePassword(PasswordDto passwordDto) {
        if (!passwordDto.password().equals(passwordDto.prePassword()))
            throw new MyBadRequestException("Passwords are not equals!");

        User user = PrincipalUtil.currentUser();
        user.setPassword(passwordDto.password());
        userRepository.save(user);

        return ApiResponse.success(userMapper.toDto(user));
    }

    @Override
    public ApiResponse<List<UserDto>> myBlockedUsers() {
        return ApiResponse.success(userMapper.toDtoList(PrincipalUtil.currentUser().getBlackList()));
    }
}
