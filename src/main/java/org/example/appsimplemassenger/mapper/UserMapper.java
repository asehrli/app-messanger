package org.example.appsimplemassenger.mapper;

import org.example.appsimplemassenger.controller.UserDto;
import org.example.appsimplemassenger.entity.User;
import org.example.appsimplemassenger.payload.RegisterDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterDto registerDto);

    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> all);
}
