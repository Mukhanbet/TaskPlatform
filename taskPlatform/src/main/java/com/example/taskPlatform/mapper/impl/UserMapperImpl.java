package com.example.taskPlatform.mapper.impl;

import com.example.taskPlatform.dto.user.UserResponse;
import com.example.taskPlatform.entities.User;
import com.example.taskPlatform.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setPassword(user.getPassword());
        return userResponse;
    }

    @Override
    public List<UserResponse> toDtoS(List<User> all) {
        List<UserResponse> userResponses = new ArrayList<>();
        for(User user : all) {
            userResponses.add(toDto(user));
        }
        return userResponses;
    }
}
