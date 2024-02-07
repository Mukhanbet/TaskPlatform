package com.example.taskPlatform.service.impl;

import com.example.taskPlatform.dto.task.TaskRequest;
import com.example.taskPlatform.dto.task.TaskResponse;
import com.example.taskPlatform.entities.Task;
import com.example.taskPlatform.entities.TaskLevel;
import com.example.taskPlatform.entities.User;
import com.example.taskPlatform.enums.Level;
import com.example.taskPlatform.exception.BadCredentialsException;
import com.example.taskPlatform.exception.NotFoundException;
import com.example.taskPlatform.mapper.TaskMapper;
import com.example.taskPlatform.repositories.TaskLevelRepository;
import com.example.taskPlatform.repositories.TaskRepository;
import com.example.taskPlatform.repositories.UserRepository;
import com.example.taskPlatform.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final TaskLevelRepository taskLevelRepository;
    @Override
    public List<TaskResponse> getAll() {
        return taskMapper.toDtoS(taskRepository.findAll());
    }

    @Override
    public List<TaskResponse> getAllAvailableTasks() {
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()) {
            if(task.isAvailable()) {
                tasks.add(task);
            }
        }
        if(tasks.isEmpty()) {
            throw new NotFoundException("There is no any available tasks! ", HttpStatus.NOT_FOUND);
        }
        return taskMapper.toDtoS(tasks);
    }

    @Override
    public List<TaskResponse> getAllArchivedTasks() {
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()) {
            if(!task.isAvailable()) {
                tasks.add(task);
            }
        }
        if(tasks.isEmpty()) {
            throw new NotFoundException("There is no any task in archive!", HttpStatus.NOT_FOUND);
        }
        return taskMapper.toDtoS(tasks);
    }

    @Override
    public List<TaskResponse> getTasksByLevel(String level) {
        if(taskLevelRepository.findByLevel(level).isEmpty()) {
            throw new BadCredentialsException("There is no like this (" + level + ") level in system");
        }
        List<Task> tasks = new ArrayList<>();
        for(Task task : taskRepository.findAll()) {
            if(task.getTaskLevel().getLevel().equals(level)) {
                tasks.add(task);
            }
        }
        return taskMapper.toDtoS(tasks);
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
    public void cancelByName(String name) {
        Optional<Task> task = taskRepository.findByName(name);
        checker(task, name);
        task.get().setAvailable(false);
        taskRepository.save(task.get());
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
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(taskRequest.getLevel());
        if(taskLevel.isEmpty()) {
            throw new NotFoundException(taskRequest.getLevel() + " level not exist in system!", HttpStatus.NOT_FOUND);
        }
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setAvailable(true);
        task.setTaskLevel(taskLevel.get());
        task.setCreatedDay(LocalDate.now());
        task.setUser(user.get());
        taskRepository.save(task);
        user.get().getTasks().add(task);
        userRepository.save(user.get());
    }

    @Override
    public void assignTaskToUser(String taskName, String userEmail) {
        Optional<Task> task = taskRepository.findByName(taskName);
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isEmpty()) {
            throw new NotFoundException("User with email " + userEmail + " not found", HttpStatus.NOT_FOUND);
        }
        task.get().setSolver(userEmail);
        taskRepository.save(task.get());
    }

    private void checker(Optional<Task> task, String name) {
        if(task.isEmpty()) {
            throw new NotFoundException("Task not found with name: " + name, HttpStatus.NOT_FOUND);
        }
    }
}
