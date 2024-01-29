package com.example.taskPlatform.controllers;

import com.example.taskPlatform.dto.user.UserRequest;
import com.example.taskPlatform.dto.user.UserResponse;
import com.example.taskPlatform.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final MyUserDetailsService service;

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public UserResponse findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @PutMapping("/updateByEmail/{email}")
    public void updateByEmail(@PathVariable String email, @RequestBody UserRequest userRequest) {
        service.updateByEmail(email, userRequest);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public void deleteByEmail(@PathVariable String email) {
        service.deleteByEmail(email);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRequest userRequest) {
        service.register(userRequest);
    }
}
