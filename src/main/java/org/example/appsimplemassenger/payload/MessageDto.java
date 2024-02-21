package org.example.appsimplemassenger.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.appsimplemassenger.controller.UserDto;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    UUID id;
    String text;
    AttachmentDto attachment;
    UserDto user;
    Timestamp createdAt;
    Timestamp updatedAt;
}
