package org.example.appsimplemassenger.controller;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.appsimplemassenger.payload.AttachmentDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    UUID id;
    String firstname;
    String lastname;
    String email;
    AttachmentDto attachment;
    Timestamp createdAt;
}
