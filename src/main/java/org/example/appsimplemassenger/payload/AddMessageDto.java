package org.example.appsimplemassenger.payload;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record AddMessageDto(@NotNull(message = "chatId is required!") UUID chatId,
                            String text,
                            MultipartFile file) {
}
