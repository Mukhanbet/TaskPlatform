package com.example.taskPlatform.service.impl;

import com.example.taskPlatform.config.JwtService;
import com.example.taskPlatform.dto.login.AuthLoginRequest;
import com.example.taskPlatform.dto.login.AuthLoginResponse;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
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
        user.setRole("USER");
        user.setPassword(encoder.encode(userRequest.getPassword()));
        userRepository.save(user);
    }

    @Override
    public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
        Optional<User> user = userRepository.findByEmail(authLoginRequest.getEmail());
        if(user.isEmpty()) {
            throw new NotFoundException("User with email: " + authLoginRequest.getEmail() + " not found!", HttpStatus.NOT_FOUND);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authLoginRequest.getEmail(), authLoginRequest.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("User not found!");
        }
        return convertToResponse(user);
    }

    private AuthLoginResponse convertToResponse(Optional<User> user) {
        AuthLoginResponse authLoginResponse = new AuthLoginResponse();
        authLoginResponse.setId(user.get().getId());
        authLoginResponse.setEmail(user.get().getEmail());
        authLoginResponse.setName(user.get().getName());

        Map<String, Object> extraClaims = new HashMap<>();
        String token = jwtService.generateToken(extraClaims, user.get());
        authLoginResponse.setToken(token);
        return authLoginResponse;
    }

    private void checker(Optional<User> user, String email) {
        if (user.isEmpty()) {
            throw new NotFoundException("User not found with the email: " + email, HttpStatus.NOT_FOUND);
        }
    }
}
