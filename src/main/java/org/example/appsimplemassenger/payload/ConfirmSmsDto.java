package org.example.appsimplemassenger.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ConfirmSmsDto(@NotBlank(message = "email is required")
                            @Email(message = "Email is invalid") String email,
                            @NotBlank String code) {
}
