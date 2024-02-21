package org.example.appsimplemassenger.payload;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddChatDto(@NotNull(message = "receiverId is required") UUID receiverId) {
}
