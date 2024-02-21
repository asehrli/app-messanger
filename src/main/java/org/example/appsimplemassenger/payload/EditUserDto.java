package org.example.appsimplemassenger.payload;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record EditUserDto(@NotBlank(message = "Firstname is required") String firstname,
                          @NotBlank(message = "Lastname is required") String lastname,
                          MultipartFile photo
) {
}
