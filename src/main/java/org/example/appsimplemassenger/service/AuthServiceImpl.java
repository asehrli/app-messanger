package org.example.appsimplemassenger.service;

import lombok.RequiredArgsConstructor;
import org.example.appsimplemassenger.entity.Attachment;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.exception.MyBadRequestException;
import org.example.appsimplemassenger.exception.MyConflictException;
import org.example.appsimplemassenger.exception.MyNotFoundException;
import org.example.appsimplemassenger.mapper.UserMapper;
import org.example.appsimplemassenger.payload.*;
import org.example.appsimplemassenger.repository.UserRepository;
import org.example.appsimplemassenger.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final Map<UUID, String> smsCodes = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private SendSmsService sendSmsService;

    @Override
    public ApiResponse<String> register(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository.findByEmail(registerDto.email());

        User user;

        if (optionalUser.isPresent()) {
            if (optionalUser.get().isEnabled())
                throw new MyConflictException("Email already taken!");

            user = optionalUser.get();
        } else {
            User entity = userMapper.toEntity(registerDto);
            user = userRepository.save(entity);
        }

        String code = String.valueOf(random.nextInt(1000, 10000));
        sendSmsService.send(registerDto.email(), code);
        smsCodes.put(user.getId(), code);

        return ApiResponse.success("Confirm sms code");
    }

    @Override
    public ApiResponse<TokenDto> confirmSms(ConfirmSmsDto confirmSmsDto) {
        User user = userRepository.findByEmail(confirmSmsDto.email()).orElseThrow(() -> new MyNotFoundException("User not found by email!"));
        if (!smsCodes.get(user.getId()).equals(confirmSmsDto.code()))
            throw new MyBadRequestException("Code is wrong!");

        user.setEnabled(true);
        userRepository.save(user);

        String token = jwtProvider.generateToken(user.getEmail());
        return ApiResponse.success(new TokenDto(token));
    }

    @Override
    public ApiResponse<TokenDto> login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.email()).orElseThrow(() -> new MyNotFoundException("User not found"));
        if (!passwordEncoder.matches(loginDto.password(), user.getPassword()))
            throw new MyNotFoundException("Password is not equals");

        String token = jwtProvider.generateToken(loginDto.email());
        return ApiResponse.success(new TokenDto(token));
    }
}
