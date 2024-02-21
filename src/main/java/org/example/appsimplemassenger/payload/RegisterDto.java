package org.example.appsimplemassenger.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record RegisterDto(@NotBlank(message = "Email is required")
                          @Email(message = "Email is invalid") String email) {
}
