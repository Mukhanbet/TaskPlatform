package com.example.taskPlatform.mapper;

import com.example.taskPlatform.dto.user.UserResponse;
import com.example.taskPlatform.entities.User;

import java.util.List;

public interface UserMapper {
    UserResponse toDto(User user);
    List<UserResponse> toDtoS(List<User> all);
}
