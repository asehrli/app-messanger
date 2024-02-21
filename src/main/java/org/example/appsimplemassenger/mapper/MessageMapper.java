package org.example.appsimplemassenger.mapper;

import org.example.appsimplemassenger.entity.Message;
import org.example.appsimplemassenger.payload.MessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDto toDto(Message message);
}
