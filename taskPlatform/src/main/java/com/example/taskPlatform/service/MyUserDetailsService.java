package com.example.taskPlatform.service;

import com.example.taskPlatform.dto.user.UserRequest;
import com.example.taskPlatform.dto.user.UserResponse;

import java.util.List;

public interface MyUserDetailsService {
    List<UserResponse> getAll();
    UserResponse findByEmail(String email);
    void updateByEmail(String email, UserRequest userRequest);
    void deleteByEmail(String email);
    void register(UserRequest userRequest);
}
