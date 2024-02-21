package org.example.appsimplemassenger.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.appsimplemassenger.controller.UserDto;

import java.util.UUID;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatDto {
    UUID id;
    UserDto user;
    UserDto receiver;
}
