package com.example.taskPlatform.service.impl;

import com.example.taskPlatform.dto.task_level.TaskLevelRequest;
import com.example.taskPlatform.dto.task_level.TaskLevelResponse;
import com.example.taskPlatform.entities.TaskLevel;
import com.example.taskPlatform.exception.BadCredentialsException;
import com.example.taskPlatform.exception.NotFoundException;
import com.example.taskPlatform.mapper.TaskLevelMapper;
import com.example.taskPlatform.repositories.TaskLevelRepository;
import com.example.taskPlatform.service.TaskLevelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskLevelServiceImpl implements TaskLevelService {

    private final TaskLevelRepository taskLevelRepository;
    private final TaskLevelMapper taskLevelMapper;

    @Override
    public List<TaskLevelResponse> getAll() {
        return taskLevelMapper.toDtoS(taskLevelRepository.findAll());
    }

    @Override
    public TaskLevelResponse findByLevel(String level) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        checker(taskLevel, level);
        return taskLevelMapper.toDto(taskLevel.get());
    }

    @Override
    public void updateByLevel(String level, TaskLevelRequest taskLevelRequest) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        checker(taskLevel, level);
        taskLevel.get().setLevel(taskLevelRequest.getLevel());
        taskLevel.get().setPoint(taskLevelRequest.getPoint());
        taskLevelRepository.save(taskLevel.get());
    }

    @Override
    public void deleteByLevel(String level) {
        Optional<TaskLevel> taskLevel = taskLevelRepository.findByLevel(level);
        checker(taskLevel, level);
        taskLevelRepository.deleteByLevel(level);
    }

    @Override
    public void create(TaskLevelRequest taskLevelRequest) {
        if(taskLevelRepository.findByLevel(taskLevelRequest.getLevel()).isPresent()) {
            throw new BadCredentialsException(taskLevelRequest.getLevel() + " level have already exist!");
        }
        TaskLevel taskLevel = new TaskLevel();
        taskLevel.setLevel(taskLevelRequest.getLevel());
        taskLevel.setPoint(taskLevelRequest.getPoint());
        taskLevelRepository.save(taskLevel);
    }

    private void checker(Optional<TaskLevel> taskLevel, String level) {
        if(taskLevel.isEmpty()) {
            throw new NotFoundException(level + " level not found!", HttpStatus.NOT_FOUND);
        }
    }
}
