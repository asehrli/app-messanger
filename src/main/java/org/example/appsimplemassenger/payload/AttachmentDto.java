package org.example.appsimplemassenger.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentDto {
    UUID id;
    String originalName;
    String contentType;
    long size;
    String contentUrl;
}