package com.example.taskPlatform.service;

import com.example.taskPlatform.dto.task.TaskRequest;
import com.example.taskPlatform.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAll();
    TaskResponse findByName(String name);
    void updateByName(String name, TaskRequest taskRequest);
    void deleteByName(String name);
    void create(TaskRequest taskRequest, String userEmail);
}
