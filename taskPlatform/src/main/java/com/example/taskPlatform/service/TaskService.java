package com.example.taskPlatform.service;

import com.example.taskPlatform.dto.task.TaskRequest;
import com.example.taskPlatform.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAll();
    List<TaskResponse> getAllAvailableTasks();
    List<TaskResponse> getAllArchivedTasks();
//    List<TaskResponse> getAllHardLevelTasks();
//    List<TaskResponse> getAllMiddleLevelTasks();
//    List<TaskResponse> getAllEasyLevelTasks();
    List<TaskResponse> getTasksByLevel(String level);
    TaskResponse findByName(String name);
    void updateByName(String name, TaskRequest taskRequest);
    void cancelByName(String name);
    void create(TaskRequest taskRequest, String userEmail);
    void assignTaskToUser(String taskName, String userEmail);
}
