package org.example.appsimplemassenger.mapper;

import org.example.appsimplemassenger.entity.Chat;
import org.example.appsimplemassenger.payload.ChatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatDto toDto(Chat chat);
}
