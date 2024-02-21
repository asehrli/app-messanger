package org.example.appsimplemassenger.payload;

import jakarta.validation.constraints.NotBlank;

public record PasswordDto(@NotBlank(message = "Password is required!") String password,
                          @NotBlank(message = "Pre password is required!") String prePassword) {
}
