package com.example.taskPlatform.service.impl;

import com.example.taskPlatform.dto.task.TaskRequest;
import com.example.taskPlatform.dto.task.TaskResponse;
import com.example.taskPlatform.entities.Task;
import com.example.taskPlatform.entities.User;
import com.example.taskPlatform.exception.BadCredentialsException;
import com.example.taskPlatform.exception.NotFoundException;
import com.example.taskPlatform.mapper.TaskMapper;
import com.example.taskPlatform.repositories.TaskRepository;
import com.example.taskPlatform.repositories.UserRepository;
import com.example.taskPlatform.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    @Override
    public List<TaskResponse> getAll() {
        return taskMapper.toDtoS(taskRepository.findAll());
    }

    @Override
    public TaskResponse findByName(String name) {
        Optional<Task> task = taskRepository.findByName(name);
        checker(task, name);
        return taskMapper.toDto(task.get());
    }

    @Override
    public void updateByName(String name, TaskRequest taskRequest) {
        Optional<Task> task = taskRepository.findByName(name);
        checker(task, name);
        if(taskRepository.findByName(taskRequest.getName()).isPresent() && !name.equals(taskRequest.getName())) {
            throw new BadCredentialsException("Task with name " + taskRequest.getName() + " have already exist!");
        }
        task.get().setName(taskRequest.getName());
        task.get().setDescription(taskRequest.getDescription());
        taskRepository.save(task.get());
    }

    @Override
    public void deleteByName(String name) {
        Optional<Task> task = taskRepository.findByName(name);
        checker(task, name);
        taskRepository.deleteByName(name);
    }

    @Override
    public void create(TaskRequest taskRequest, String userEmail) {
        if (taskRepository.findByName(taskRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Task with name " + taskRequest.getName() + " have already exist!");
        }
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new NotFoundException("User with email " + userEmail + " not found", HttpStatus.NOT_FOUND);
        }
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setUser(user.get());
        taskRepository.save(task);
    }

    private void checker(Optional<Task> task, String name) {
        if(task.isEmpty()) {
            throw new NotFoundException("Task not found with name: " + name, HttpStatus.NOT_FOUND);
        }
    }
}
