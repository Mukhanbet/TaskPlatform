package com.example.taskPlatform.service.impl;

import com.example.taskPlatform.dto.user.UserRequest;
import com.example.taskPlatform.dto.user.UserResponse;
import com.example.taskPlatform.entities.User;
import com.example.taskPlatform.exception.BadCredentialsException;
import com.example.taskPlatform.exception.NotFoundException;
import com.example.taskPlatform.mapper.UserMapper;
import com.example.taskPlatform.repositories.UserRepository;
import com.example.taskPlatform.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponse> getAll() {
        return userMapper.toDtoS(userRepository.findAll());
    }

    @Override
    public UserResponse findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        return userMapper.toDto(user.get());
    }

    @Override
    public void updateByEmail(String email, UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent() && !email.equals(userRequest.getEmail())) {
            throw new BadCredentialsException("user with email: " + userRequest.getEmail() + " is already exist!");
        }
        user.get().setName(userRequest.getName());
        user.get().setEmail(userRequest.getEmail());
        user.get().setPassword(userRequest.getPassword());
        userRepository.save(user.get());
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        userRepository.deleteByEmail(email);
    }

    @Override
    public void register(UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("user with email: " + userRequest.getEmail() + " is already exist!");
        }
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setRole("EMPLOYEE");
        user.setPassword(userRequest.getPassword());
        userRepository.save(user);
    }

    private void checker(Optional<User> user, String email) {
        if (user.isEmpty()) {
            throw new NotFoundException("User not found with the email: " + email, HttpStatus.NOT_FOUND);
        }
    }
}
